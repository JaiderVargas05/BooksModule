package edu.eci.cvds.Books.Domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID book_id;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Copy> copies;

    private String ISBN;
    private String description;
    private String title;
    private String author;
    private String editorial;
    private String edition;
    private Integer year;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_subcategory_ map",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "book_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "subcategory_id",
                    referencedColumnName = "subcategory_id"
            )
    )
    private List<Subcategory> subcategories;

    public Book(){

    }
    public Book(String ISBN, String description, String title, String author, String editorial, String edition, Integer year) {
        this.ISBN = ISBN;
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
    public UUID getBook_id() {
        return book_id;
    }

    public void setBook_id(UUID book_id) {
        this.book_id = book_id;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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
