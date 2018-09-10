package com.fourdsight.sitecapture.website.dto;

import com.fourdsight.sitecapture.website.SiteStateEnum;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

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
