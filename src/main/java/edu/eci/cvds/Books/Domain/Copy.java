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
    @Column(length = 30)
    private String barCode;
    @Enumerated(EnumType.STRING)
    private CopyDispo disponibility = CopyDispo.AVAILABLE;
    private boolean active = true;

    public Copy(Book book, String state) {
        this.state = CopyState.valueOf(state.toUpperCase());
        this.book = book;
    }
    public Copy() {
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBook() {
        return this.book.getBookId();
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
