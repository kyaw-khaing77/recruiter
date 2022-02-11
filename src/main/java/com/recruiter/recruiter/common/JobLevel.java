package com.recruiter.recruiter.common;

public enum JobLevel {
    ENTRY_LEVEL("Entry Level"),
    MANAGER("Manager"),
    EXPERIENCED_NON_MANAGER("Experienced Non-Manager"),
    DIRECTOR_AND_ABOVE("Director and above");

    private final String value;

    JobLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
