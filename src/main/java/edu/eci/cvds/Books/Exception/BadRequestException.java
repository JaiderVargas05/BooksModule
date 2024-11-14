package edu.eci.cvds.Books.Exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String object, String id) {
        super(object + " with ID " + id + " has invalid data.");
    }
}