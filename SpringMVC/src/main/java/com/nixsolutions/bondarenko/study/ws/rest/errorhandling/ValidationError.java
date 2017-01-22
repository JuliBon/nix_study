package com.nixsolutions.bondarenko.study.ws.rest.errorhandling;

public class ValidationError {
    private String name; //User field name
    private String message;

    public ValidationError() {
    }

    public ValidationError(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
