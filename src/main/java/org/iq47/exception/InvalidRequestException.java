package org.iq47.exception;

public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 123247653489L;

    private final String message;

    public InvalidRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
