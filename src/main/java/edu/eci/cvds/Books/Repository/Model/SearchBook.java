package edu.eci.cvds.Books.Repository.Model;

public interface SearchBook {
    String getBookId();
    String getTitle();
    String getAuthor();
    String getIsbn();
    String getRecommendedAges();
    String getImgPath();
    String getDescription();
}
