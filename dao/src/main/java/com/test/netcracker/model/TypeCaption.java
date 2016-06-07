package com.test.netcracker.model;

/**
 * Enum class for customer types.
 * Contains three types.
 * Created by IPahomov on 03.06.2016.
 */
public enum TypeCaption {
    SMALL("Small/Medium Business"),
    RESIDENTAL("Residental"),
    ENTERPRISE("Enterprise");

    String type;

    TypeCaption(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
