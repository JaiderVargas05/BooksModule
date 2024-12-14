package edu.eci.cvds.Books.Exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String object, String id) {
        super( object + " with ID: " + id + " was not found.");
    }



}
