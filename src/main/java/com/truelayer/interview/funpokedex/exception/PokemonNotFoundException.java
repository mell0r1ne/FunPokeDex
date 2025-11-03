package com.truelayer.interview.funpokedex.exception;

/**
 * Exception thrown when a Pokemon is not found by name.
 * Results in HTTP 404 response.
 */
public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(String message) {
        super(message);
    }

    public PokemonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
