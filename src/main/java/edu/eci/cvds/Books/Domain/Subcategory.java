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
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    private String description;

    public Subcategory(){

    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description=description;
    }
}
