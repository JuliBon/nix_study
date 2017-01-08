package com.nixsolutions.bondarenko.study.exception;

public class NotUniqueLoginException extends RuntimeException {

    public NotUniqueLoginException() {
        super("Not unique login");
    }
}
