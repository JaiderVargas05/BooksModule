package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

@Entity
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Enumerated(EnumType.STRING)
    private CopyState state;
    private String barCode;
    @Enumerated(EnumType.STRING)
    private CopyDispo disponibility;
    private boolean active = true;

    public Copy(Book book, String state) {
        this.disponibility = CopyDispo.AVAILABLE;
        this.state = CopyState.valueOf(state.toUpperCase());
        this.book = book;
        //generateCodeBar();
    }
    public Copy() {
        this.disponibility = CopyDispo.AVAILABLE;
    }
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
