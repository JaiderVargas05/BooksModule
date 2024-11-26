package edu.eci.cvds.Books.Controller.RequestModel;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.CopyDispo;
import edu.eci.cvds.Books.Domain.CopyState;

public class CopyRequest {
    private String id;
    private Book book;

    private String bookId;

    private String isbn;
    private String state;
    private String barCode;
    private String ubication;
    private CopyDispo disponibility;
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public CopyDispo getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(CopyDispo disponibility) {
        this.disponibility = disponibility;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
