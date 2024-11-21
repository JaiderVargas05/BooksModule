package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Book> books;
    @OneToMany(mappedBy = "category", cascade=CascadeType.ALL)
    private List<Subcategory> subcategories;
    private boolean active=true;

    public Category(){
    }

    public Category(String categoryId, String description, List<Book> books, List<Subcategory> subcategories, boolean active) {
        this.categoryId = categoryId;
        this.description = description;
        this.books = books;
        this.subcategories = subcategories;
        this.active = active;
    }

    public String getCategoryId() {
        return categoryId;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return books;
    }
    public List<String> getSubcategories(){
        return subcategories.stream()
                .map(Subcategory::getDescription)
                .collect(Collectors.toList());
    }

}
