package edu.eci.cvds.Books.Controller.RequestModel;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.CopyDispo;
import edu.eci.cvds.Books.Domain.CopyState;

public class CopyRequest {
    private String id;
    private Book book;
    private CopyState state;
    private String barCode;
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

    public CopyState getState() {
        return state;
    }

    public void setState(CopyState state) {
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
}
