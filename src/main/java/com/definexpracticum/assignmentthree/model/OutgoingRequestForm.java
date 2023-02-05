package com.definexpracticum.assignmentthree.model;


import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;


public class OutgoingRequestForm {

    private String location;
    private String interval;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
