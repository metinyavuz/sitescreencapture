package com.fourdsight.sitecapture.website;

import com.fourdsight.sitecapture.website.dto.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("site")
public class WebsiteController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WebsiteService websiteService;

    @PostMapping("capture")
    public ResponseEntity<?> captureSite(@RequestParam String[] urls) {
        if (urls !=null) {
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

            return ResponseEntity.ok(websites);
        }
        return ResponseEntity.noContent().build();
    }
}
