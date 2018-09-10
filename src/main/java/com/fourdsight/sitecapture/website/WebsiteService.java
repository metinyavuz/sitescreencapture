package com.fourdsight.sitecapture.website;

import com.fourdsight.sitecapture.website.capture.CaptureState;
import com.fourdsight.sitecapture.website.capture.WebsiteCaptureProcessor;
import com.fourdsight.sitecapture.website.dto.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

@Service
public class WebsiteService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${website.screen.upload.path}")
    private String uploadPath="./upload/";

    @Async("websiteCaptureTaskExecutor")
    public CompletableFuture<Website> processSiteCapture(String url){
        Website website =  new Website(url);
        log.debug("Url:" + website + " process start");
        LocalDateTime startTime = LocalDateTime.now();
        try {
            CaptureState capState = WebsiteCaptureProcessor.captureWebsiteScreen(website.getUrl(),uploadPath);
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
        return CompletableFuture.completedFuture(website);
    }
}
