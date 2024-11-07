package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookId;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Copy> copies;


    private String description;
    private String title;
    private String author;
    private String editorial;
    private String edition;
    private String isbn;

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

    private Integer year;
    private boolean active = true;
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
    public Book(String isbn, String description, String title, String author, String editorial, String edition, Integer year) {
        this.isbn = isbn;
        this.description = description;
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.edition = edition;
        this.year = year;
    }
    public List<Subcategory> getSubcategories() {
        return subcategories;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Copy> getEjemplares() {
        return copies;
    }
    public void setEjemplares(List<Copy> ejemplares) {
        this.copies = ejemplares;
    }
    public Copy getEjemplar(int index){
        return copies.get(index);
    }
}
