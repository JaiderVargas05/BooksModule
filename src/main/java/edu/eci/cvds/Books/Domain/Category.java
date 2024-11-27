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
    @Column(unique = true)
    private String description;
    private boolean active=true;

    public Category(){
    }

    public Category(String categoryId, String description, boolean active) {
        this.categoryId = categoryId;
        this.description = description;
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

}
