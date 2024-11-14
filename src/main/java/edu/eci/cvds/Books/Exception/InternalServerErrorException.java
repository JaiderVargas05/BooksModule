package edu.eci.cvds.Books.Exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String object, String id) {
        super(object + " with ID " + id + " caused an internal error.");
    }
}