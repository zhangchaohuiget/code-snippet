package com.app.model;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Job {
    @XStreamAsAttribute
    private String status;

    private String jobName;

    private int jobLife;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getJobLife() {
        return jobLife;
    }

    public void setJobLife(int jobLife) {
        this.jobLife = jobLife;
    }

}
