package com.nixsolutions.bondarenko.study.exception;

public class NotUniqueLoginException extends Exception {

    public NotUniqueLoginException() {
        super("Not unique login");
    }
}
