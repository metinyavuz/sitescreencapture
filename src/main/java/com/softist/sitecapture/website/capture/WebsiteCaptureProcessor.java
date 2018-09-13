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
package com.softist.sitecapture.website.capture;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author Metin Yavuz(CreByM)
 */

@Component
public class WebsiteCaptureProcessor {

    @Value("${website.screen.upload.path}")
    private String uploadPath="./upload/";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public CaptureState captureWebsiteScreen(String url) {
        CaptureState urlState = new CaptureState(url);

        // Open ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Maximize the window
            driver.manage().window().maximize();
            driver.get(url);

            urlState.setImagePath(captureScreenShot(driver));
        }
        catch (IOException io) {
            log.error("Website Service getting a web drive exception for url:" + url, io);
            urlState.setErrorMessage("IO exception occur");
        }
        catch (WebDriverException ewd) {
            log.error("Website Service getting a web drive exception for url:" + url,ewd);
            urlState.setErrorMessage(driver.getClass().getName() +"Web drive exception contact with support");
        }catch (Exception ex) {
            log.error("An exception occur while capture screen capture for url:" + url, ex);
            urlState.setErrorMessage(ex.getMessage());
        }
        finally {
            // Quit driver
            driver.quit();
        }

        return urlState;
    }

    public String captureScreenShot(WebDriver driver) throws IOException {
        // Take screenshot and store as a file format
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String fileName =  System.currentTimeMillis() + ".png";
        // now copy the screenshot to desired location using copyFile method
        FileUtils.copyFile(src, new File(uploadPath + fileName));
        return "/site/screenshot/" + fileName;
    }

    public String getUploadPath(){
        return uploadPath;
    }

}

