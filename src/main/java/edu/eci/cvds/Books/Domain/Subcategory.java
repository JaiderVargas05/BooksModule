package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID subcategory_id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private String description;

    public Subcategory(){

    }
}
