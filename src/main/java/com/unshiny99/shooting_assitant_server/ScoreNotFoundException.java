package com.unshiny99.shooting_assitant_server;

public class ScoreNotFoundException extends RuntimeException {

    public ScoreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
