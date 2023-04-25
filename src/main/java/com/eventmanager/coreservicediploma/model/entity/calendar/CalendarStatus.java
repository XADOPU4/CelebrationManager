package com.eventmanager.coreservicediploma.model.entity.calendar;

public enum CalendarStatus {
    BUSY("BUSY"),
    WORKING("WORKING"),
    DELETED("DELETED");
    private final String name;

    CalendarStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
