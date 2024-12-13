package edu.eci.cvds.Books.Exception;

public class BadAvailabilityException extends RuntimeException{
    public BadAvailabilityException(String message) {
        super(message);
    }
    public BadAvailabilityException(String object, String id) {
        super("Invalid availability for " + object  + " ID: " + id);
    }
}
