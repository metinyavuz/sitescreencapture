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
package com.softist.sitecapture.website.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Metin Yavuz(CreByM)
 */
public class Website implements Serializable{
    private String url;
    private String captureUrl;
    private SiteStateEnum state = SiteStateEnum.INIT;
    private String note;
    private LocalDateTime startTime;
    private long duration;

    public Website(String url) {
        this.url = url;
        this.startTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Website{" +
                "url='" + url + '\'' +
                ", state=" + state +
                ", startTime=" + startTime +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaptureUrl() {
        return captureUrl;
    }

    public void setCaptureUrl(String captureUrl) {
        this.captureUrl = captureUrl;
    }

    public SiteStateEnum getState() {
        return state;
    }

    public void setState(SiteStateEnum state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
