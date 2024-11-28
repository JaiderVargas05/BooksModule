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
    //@Enumerated(EnumType.STRING)
    private String state;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String barCode;
    private String ubication;
    @Enumerated(EnumType.STRING)
    private CopyDispo disponibility = CopyDispo.AVAILABLE;
    private boolean active = true;

    public Copy(Book book, String state) {
        this.state = state;
        this.book = book;
    }
    public Copy(Book book, String state, String ubication) {
        this.state = state;
        this.book = book;
        this.ubication = ubication;
    }
    public Copy() {
    }

    public Copy(String id, Book book, String state, String barCode, CopyDispo disponibility, boolean active) {
        this.id = id;
        this.book = book;
        this.state = state;
        this.barCode = barCode;
        this.disponibility = disponibility;
        this.active = active;
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
}
