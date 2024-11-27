package edu.eci.cvds.Books.Exception;

public class DuplicateObjectException extends RuntimeException {
    public DuplicateObjectException(String message) {
        super(message);
    }
    public DuplicateObjectException(String object, String description) {
        super(object +" "+ description+" already exists.");
    }

}
