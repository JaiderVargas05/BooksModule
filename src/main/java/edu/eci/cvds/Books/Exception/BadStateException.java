package edu.eci.cvds.Books.Exception;

public class BadStateException extends RuntimeException{
    public BadStateException(String message) {
        super(message);
    }
    public BadStateException(String object, String id) {
        super( object + " with  " + id + " contains invalid state.");
    }

}
