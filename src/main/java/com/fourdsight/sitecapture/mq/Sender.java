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
package com.fourdsight.sitecapture.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Metin Yavuz(CreByM)
 */

public class Sender {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, Object message) {
        log.info("sending message='{}' to destination='{}'", message, destination);
        jmsTemplate.convertAndSend(destination, message);
    }
}