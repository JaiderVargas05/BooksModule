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

    public List<String> getBooks() {
        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }
    public List<String> getSubcategories(){
        return subcategories.stream()
                .map(Subcategory::getDescription)
                .collect(Collectors.toList());
    }

}
