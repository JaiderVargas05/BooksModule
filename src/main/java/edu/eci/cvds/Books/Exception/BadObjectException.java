package edu.eci.cvds.Books.Exception;

public class BadObjectException extends RuntimeException{
    public BadObjectException(String message) {
        super(message);
    }
    public BadObjectException(String object, String id) {
        super( object + " with " + id + " is incomplete data.");
    }

}

