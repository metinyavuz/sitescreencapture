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

import com.fourdsight.sitecapture.website.dto.Website;
import com.fourdsight.sitecapture.website.util.ValidationUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Metin Yavuz(CreByM)
 */

@RestController
@RequestMapping("site")
public class SiteController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebsiteService websiteService;

    @PostMapping("capture")
    public ResponseEntity<?> captureSite(@RequestParam String[] urls) {
        if (urls !=null) {
            log.info("Capture request started urls size: " + urls.length);
            // Creating Future Stream
            List<CompletableFuture<Website>> futures = new ArrayList();
            for(String url:urls){
                futures.add(websiteService.processSiteCapture(url));
            }

            // Join All Future
            List<Website> websites =
                    futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());

            log.info("Capture request finished urls size: " + urls.length);
            return ResponseEntity.ok(websites);
        }
        return ResponseEntity.noContent().build();
    }

   @GetMapping("screenshot/{imageName}")
    public ResponseEntity<byte[]> screenshot(@PathVariable String imageName) {
        if (StringUtils.hasText(imageName)) {
            byte[] image = websiteService.getWebsiteScreenShot(imageName);
            if (image != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);

                return new ResponseEntity<byte[]> ( image, headers, HttpStatus.CREATED);
            } else
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
/*
    @GetMapping("screenshot/{imageName}")
    public void getImageAsByteArray(@PathVariable String imageName,HttpServletResponse response) throws IOException {
        BufferedImage image = websiteService.getWebsiteScreenShot(imageName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        ImageIO.write(image, "png", response.getOutputStream());
    }
    */
}
