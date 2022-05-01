package org.iq47.exception;

public class ItemSaveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public ItemSaveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
