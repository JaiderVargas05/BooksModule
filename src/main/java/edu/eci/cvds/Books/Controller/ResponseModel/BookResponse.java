package edu.eci.cvds.Books.Controller.ResponseModel;


import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class BookResponse extends Response<List<Book>> {
    public static final String SUCCESS_BOOK_SAVED = "Book saved successfully";
    public static final String SUCCESS_BOOK_RETRIEVED = "Book retrieved successfully";
    public static final String SUCCESS_BOOK_UPDATED = "Book updated successfully";
    public static final String SUCCESS_BOOK_DELETED = "Book deleted successfully";

    public BookResponse(HttpStatus status, String message, List<Book> body) {
        super(status, message, body);
    }
    public BookResponse(HttpStatus status, String message, Book book) {
        super(status, message, Collections.singletonList(book));
    }
}

