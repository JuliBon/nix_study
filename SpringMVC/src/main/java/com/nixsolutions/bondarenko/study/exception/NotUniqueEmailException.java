package com.nixsolutions.bondarenko.study.exception;

public class NotUniqueEmailException extends Exception{

    public NotUniqueEmailException() {
        super("Not unique email");
    }
}
