package edu.eci.cvds.Books.Exception;

public class NotNullException  extends RuntimeException{

    public NotNullException (String message) {
        super(message);
    }
    public NotNullException (String object, String id) {
        super( object + " must not be " + id);
    }
}
