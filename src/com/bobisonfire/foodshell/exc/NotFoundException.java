package com.bobisonfire.foodshell.exc;

public class NotFoundException extends RuntimeException {
    protected String name;

    NotFoundException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}