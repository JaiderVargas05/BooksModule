package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;

@Entity
public class Book {
    public String title;
    public String author;
    public String editorial;
    public Integer year;
    @Id
    public BigInteger ISBN;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Ejemplar> ejemplars;

    public Book(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigInteger getISBN() {
        return ISBN;
    }

    public void setISBN(BigInteger ISBN) {
        this.ISBN = ISBN;
    }

    public List<Ejemplar> getEjemplares() {
        return ejemplars;
    }
    public void setEjemplares(List<Ejemplar> ejemplares) {
        this.ejemplars = ejemplares;
    }
    public Ejemplar getEjemplar(int index){
        return ejemplars.get(index);
    }
}
