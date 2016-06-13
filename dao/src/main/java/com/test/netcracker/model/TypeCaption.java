package com.test.netcracker.model;

import java.io.Serializable;

/**
 * Enum class for customer types.
 * Contains three types.
 * Created by IPahomov on 03.06.2016.
 */
public enum TypeCaption implements Serializable {
    SMALL_MEDIUM_BUSINESS("Small/Medium Business"),
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

    public static TypeCaption findType(String type){
        for (TypeCaption tc : TypeCaption.values()){
            if(tc.getType().contains(type))
                return tc;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
