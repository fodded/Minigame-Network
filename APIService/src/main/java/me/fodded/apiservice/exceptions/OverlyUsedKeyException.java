package me.fodded.apiservice.exceptions;

public class OverlyUsedKeyException extends RuntimeException {

    public OverlyUsedKeyException(String message) {
        super(message);
    }
}
