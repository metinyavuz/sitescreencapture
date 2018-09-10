/*-----------------------------------------------------------
 * Project : SiteCapture
 * Organization : Softist (http://www.softist.com.tr)
 * Contents :
 *
 *-----------------------------------------------------------
 * Copyright (c) 2018 Softist All Rights Reserved.
 *-----------------------------------------------------------
 * Revision History:
 * who                  when               what
 * Metin Yavuz(CreByM) Sep 10, 2018	Created
 *-----------------------------------------------------------
 */
package com.fourdsight.sitecapture.website;

import com.fourdsight.sitecapture.mq.Sender;
import com.fourdsight.sitecapture.website.capture.CaptureState;
import com.fourdsight.sitecapture.website.capture.WebsiteCaptureProcessor;
import com.fourdsight.sitecapture.website.dto.SiteStateEnum;
import com.fourdsight.sitecapture.website.dto.Website;
import com.fourdsight.sitecapture.website.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
/**
 * @author Metin Yavuz(CreByM)
 */
@Service
public class WebsiteService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebsiteCaptureProcessor captureProcessor;

    @Autowired
    private Sender sender;

    @Value("${activemq.queue.savewebsite}")
    private String senderSaveWebsiteDestination;

    @Async("websiteCaptureTaskExecutor")
    public CompletableFuture<Website> processSiteCapture(String url){
        Website website =  new Website(url);
        log.debug("Url:" + website + " process start");
        LocalDateTime startTime = LocalDateTime.now();
        try {
            CaptureState capState = captureProcessor.captureWebsiteScreen(website.getUrl());
            if (StringUtils.hasText(capState.getImagePath())) {
                website.setState(SiteStateEnum.SUCCESS);
                website.setCaptureUrl(capState.getImagePath());
            } else {
                website.setState(SiteStateEnum.FAIL);
                website.setNote(capState.getErrorMessage());
            }
        }
        catch (Exception ex) {
            log.error("Website Service getting an exception for url:" + website,ex);
            website.setState(SiteStateEnum.FAIL);
            website.setNote(ex.getMessage());
        }
        website.setDuration(ChronoUnit.MILLIS.between(startTime,LocalDateTime.now()));
        log.debug("Url:" + website + " process finished. Duration :" + website.getDuration() );

        // Sending result JMS for save
        sender.send(senderSaveWebsiteDestination,website);
        return CompletableFuture.completedFuture(website);
    }

    public byte[] getWebsiteScreenShot(String imageName){
        byte[] result = null;
        if (ValidationUtil.checkFileName(imageName)) {
            // read image from folder
            File folderInput = new File(captureProcessor.getUploadPath() + imageName);
            try {
                result = Files.readAllBytes(folderInput.toPath());
            } catch (IOException e) {
                log.error("getSiteScreenShot getting an exception for image " + imageName, e);
            }
        }
        else
            log.info("getWebsiteScreenShot file name not valid , file:" + imageName);
        return result;
    }
}
