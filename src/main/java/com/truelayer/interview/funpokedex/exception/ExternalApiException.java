package com.truelayer.interview.funpokedex.exception;

/**
 * Exception thrown when external API calls fail.
 * Results in HTTP 503 Service Unavailable response.
 */
public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
