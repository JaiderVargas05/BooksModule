package edu.eci.cvds.Books.Exception;

public class BadFormatException extends RuntimeException{
    public BadFormatException(String message) {
        super(message);
    }
    public BadFormatException(String object, String id) {
        super( object + " with  " + id + " format is incorrect.");
    }
}
