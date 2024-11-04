package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

import java.util.Locale;

@Entity
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Enumerated(EnumType.STRING)
    private EjemplarState state;
    private Integer barCode;
    @Enumerated(EnumType.STRING)
    private EjemplarDispo disponibility;
    private boolean active = true;

    public Ejemplar(Book book, String state) {
        this.disponibility = EjemplarDispo.AVAILABLE;
        this.state = EjemplarState.valueOf(state.toUpperCase());
        this.book = book;
        //generateCodeBar();
    }
    public Ejemplar() {
        this.disponibility = EjemplarDispo.AVAILABLE;
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
    public EjemplarState getState() {
        return state;
    }
    public void setState(EjemplarState state) {
        this.state = state;
    }
    public Integer getBarCode() {
        return barCode;
    }
    public void setBarCode(Integer barCode) {
        this.barCode = barCode;
    }
    public EjemplarDispo getDisponibility() {
        return disponibility;
    }
    public void setDisponibility(EjemplarDispo disponibility) {
        this.disponibility = disponibility;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

}
