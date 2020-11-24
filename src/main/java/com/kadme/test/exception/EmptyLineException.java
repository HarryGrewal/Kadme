package com.kadme.test.exception;

public class EmptyLineException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmptyLineException(String message) {
        super("Everything breaks, so did I! Hva vil du mere?");
    }

}
