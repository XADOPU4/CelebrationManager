package com.eventmanager.coreservicediploma.model.entity.event;

public enum EventStatus {
    CREATED("CREATED"),
    STARTED("STARTED"),
    ENDED("ENDED"),
    DISCARDED("DISCARDED");

    private final String name;

    EventStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
