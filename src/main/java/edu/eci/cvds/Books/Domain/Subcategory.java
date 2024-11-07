package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String subcategoryId;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private String description;

    public Subcategory(){

    }
}
