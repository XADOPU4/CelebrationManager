package com.eventmanager.coreservicediploma.model.entity.resource;

public enum ResourceType {
    DISPOSABLE("DISPOSABLE"),
    REUSABLE("REUSABLE");

    private String name;

    ResourceType(String name) {
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
