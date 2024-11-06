package edu.eci.cvds.Books.Exception;

public class BookException extends Exception {


    public static final String INVALID_BOOK_DATA = "The provided book data is invalid. Please check the input fields.";
    public static final String BOOK_ALREADY_EXISTS = "A book with this title or ISBN already exists in the system.";
    public static final String TITLE_REQUIRED = "The book title is required and cannot be empty.";
    public static final String AUTHOR_REQUIRED = "The author name is required and cannot be empty.";
    public static final String ISBN_REQUIRED = "The ISBN number is required and must be valid.";
    public static final String INVALID_ISBN = "The provided ISBN number is invalid. Please provide a valid ISBN.";

    public static final String BOOK_CREATION_FAILED = "An error occurred while creating the book. Please try again.";
    public static final String DATABASE_ERROR = "A database error occurred while trying to save the book.";
    public static final String INTERNAL_BOOK_CREATION_ERROR = "An internal error occurred while processing the book creation request.";


    public BookException(String message) {
        super(message);
    }

}