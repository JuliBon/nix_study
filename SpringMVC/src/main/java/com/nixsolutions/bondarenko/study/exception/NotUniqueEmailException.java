package com.nixsolutions.bondarenko.study.exception;

public class NotUniqueEmailException extends RuntimeException{

    public NotUniqueEmailException() {
        super("Not unique email");
    }
}
