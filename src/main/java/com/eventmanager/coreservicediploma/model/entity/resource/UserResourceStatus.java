package com.eventmanager.coreservicediploma.model.entity.resource;

public enum UserResourceStatus {
    OK("OK"),
    DAMAGED("DAMAGED"),
    DESTROYED("DAMAGED"),
    DELETED("DELETED");

    private String name;

    UserResourceStatus(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
