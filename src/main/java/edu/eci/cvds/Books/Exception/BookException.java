package edu.eci.cvds.Books.Exception;

public class BookException extends RuntimeException {
    public static final String notNull = "Book object cannot be null";
    public static final String dataNotNull = "Arguments cannot be null";
    public static final String notFound = "Book not found";
    public static final String badFormat = "The format is incorrect";
    public static final String badBook = "Cannot create Book, incomplete data";
    public static final String badValues = "The values of the Book are not valid";
    public BookException(String message) {
        super(message);
    }
}
