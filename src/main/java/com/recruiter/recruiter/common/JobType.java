package com.recruiter.recruiter.common;

public enum JobType {
    FULL_TIME("FULL TIME"),
    HALF_TIME("HALF TIME");

    private final String value;

    JobType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
