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
    private boolean active;

    public Subcategory(Book book, String subcategoryId, Category category, String description, boolean active) {
        this.book = book;
        this.subcategoryId = subcategoryId;
        this.description = description;
        this.active = active;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public Subcategory(){}
    public String getSubcategoryId() {
        return subcategoryId;
    }
    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description=description;
    }
}
