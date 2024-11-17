package edu.eci.cvds.Books.Controller.RequestModel;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BookRequest {
    private String isbn;
    private String description;
    private String title;
    private String author;
    private String editorial;
    private String edition;
    private Integer year;
    private String categoryId;
    private List<String> subcategoryIds;

    private MultipartFile img;

    public String getIsbn() {
        return isbn;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getSubcategoryIds() {
        return subcategoryIds;
    }

    public void setSubcategoryIds(List<String> subcategoryIds) {
        this.subcategoryIds = subcategoryIds;
    }
}

