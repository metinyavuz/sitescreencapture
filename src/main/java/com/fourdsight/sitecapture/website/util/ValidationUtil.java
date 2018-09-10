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
package com.fourdsight.sitecapture.website.util;

import java.util.regex.Pattern;

/**
 * @author Metin Yavuz(CreByM)
 */

public class ValidationUtil {
    private static String fileNameRegex ="\\w+(\\.(?i)(png))$";

    private static Pattern pattern = Pattern.compile(fileNameRegex);

    public static boolean checkFileName(String filename){
        return pattern.matcher(filename).matches();
    }
}
