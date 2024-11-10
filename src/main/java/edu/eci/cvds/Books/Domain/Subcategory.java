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
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Subcategory(){

    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
