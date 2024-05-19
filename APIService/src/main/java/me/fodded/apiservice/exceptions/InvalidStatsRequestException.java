package me.fodded.apiservice.exceptions;

public class InvalidStatsRequestException extends RuntimeException {

    public InvalidStatsRequestException(String message) {
        super(message);
    }
}
