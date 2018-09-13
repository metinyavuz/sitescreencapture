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
package com.softist.sitecapture.mq;

import java.util.concurrent.CountDownLatch;

import com.softist.sitecapture.website.dto.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

/**
 * @author Metin Yavuz(CreByM)
 */

public class Receiver {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${activemq.queue.savewebsite}")
    public void receive(Website website) {
        log.info("Website will persistent website='{}'", website);
        latch.countDown();
    }
}
