package edu.eci.cvds.Books.Exception;

public class BadValuesException extends RuntimeException{
    public BadValuesException(String message) {
        super(message);
    }
    public BadValuesException(String object, String id) {
        super( object + " with ID " + id + " is not valid.");
    }
}