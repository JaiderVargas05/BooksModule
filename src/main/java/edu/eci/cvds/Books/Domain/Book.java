package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.stream.Collectors;


@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookId;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Copy> copies;
    @Column(length = 2000)
    private String description;
    private String title;
    private String author;
    private String collection;
    private String editorial;
    private String edition;
    private String recommendedAges;
    private String language;
    private String isbn;
    private String imgPath;
    private boolean active = true;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_category_ map",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "bookId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "categoryId"
            )
    )
    private List<Category> categories;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_subcategory_ map",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "bookId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "subcategory_id",
                    referencedColumnName = "subcategoryId"
            )
    )
    private List<Subcategory> subcategories;

    public Book(){

    }
    public Book(String isbn, String description, String title, String author, String editorial, String edition, String collection, String recommendedAges, String language) {
        this.isbn = isbn;
        this.description = description;
        this.title = title;
        this.author = author;
        this.collection = collection;
        this.editorial = editorial;
        this.edition = edition;
        this.recommendedAges = recommendedAges;
        this.language = language;
    }

    public boolean isActive() {
        return active;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getSubcategories() {
        return subcategories.stream()
                .map(Subcategory::getDescription)
                .collect(Collectors.toList());
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
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


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Copy> getCopies() {
        return copies;
    }
    public void setCopies(List<Copy> ejemplares) {
        this.copies = ejemplares;
    }
    public Copy getEjemplar(int index){
        return copies.get(index);
    }
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<String> getCategories() {
        return categories.stream()
                .map(Category::getDescription)
                .collect(Collectors.toList());
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
}
