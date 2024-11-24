package edu.eci.cvds.Books.Controller.RequestModel;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BookRequest {
    private String bookId;
    private String isbn;
    private String description;
    private String title;
    private String author;
    private String collection;
    private String editorial;
    private String edition;
    private String recommendedAges;
    private String language;
    private List<String> categoryIds;
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

    public List<String> getCategoryIds() {
        return categoryIds;
    }
    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }
    public List<String> getSubcategoryIds() {
        return subcategoryIds;
    }
    public void setSubcategoryIds(List<String> subcategoryIds) {
        this.subcategoryIds = subcategoryIds;
    }
    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getRecommendedAges() {
        return recommendedAges;
    }

    public void setRecommendedAges(String recommendedAges) {
        this.recommendedAges = recommendedAges;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}

