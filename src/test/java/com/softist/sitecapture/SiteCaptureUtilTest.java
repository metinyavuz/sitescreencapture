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
package com.softist.sitecapture;

import com.softist.sitecapture.website.util.ValidationUtil;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Metin Yavuz(CreByM)
 */
public class SiteCaptureUtilTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String[] validFilenames ={"123.png","123aa.png","adada123.png","123AAA.png","AAA123AAA.png","AAA123ssd.png","123AAAsddssAA.png","AAA1ZZ23asd2224445aaaa.png"};
    private String[] inValidFilenames ={"./123.png","/./-.xcc","/bash/init/","/etc/passwd","../../../../../../../../../etc/passwd","/var/www/html/admin/get.inc"};
    @Test
    public void testValidFileNames(){
        // Check valid filenames
        log.info("Checking valid file ");
        for (String fileName:validFilenames) {
            log.info("Check for " + fileName);
            Assert.assertEquals(true, ValidationUtil.checkFileName(fileName));
        }

        // Check invalid filenames
        log.info("Checking invalid files ");
        for (String fileName:inValidFilenames) {
            log.info("Check for " + fileName);
            assertEquals(false,ValidationUtil.checkFileName(fileName));
        }
    }
}
