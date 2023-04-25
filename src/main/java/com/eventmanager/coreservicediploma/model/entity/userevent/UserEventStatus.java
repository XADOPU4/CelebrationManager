package com.eventmanager.coreservicediploma.model.entity.userevent;

public enum UserEventStatus {
    INVITED("INVITED"),
    APPROVED("APPROVED"),
    CANCELLED("CANCELLED"),
    DELETED("DELETED");

    private final String name;

    UserEventStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
