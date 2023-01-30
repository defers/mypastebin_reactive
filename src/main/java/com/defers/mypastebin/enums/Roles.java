package com.defers.mypastebin.enums;

public enum Roles {
    ADMIN("ADMIN"),
    USER("USER)");

    private String roleName;

    Roles(String roleName){
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

}
