package edu.eci.cvds.Books.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigInteger;

@Entity
public class Book {
    public String title;
    public String author;
    public String editorial;
    public Integer year;
    @Id
    public BigInteger ISBN;
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
}
