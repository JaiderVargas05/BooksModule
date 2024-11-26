package edu.eci.cvds.Books.Repository.Model;

public interface BasicBook {
    String getBookId();
    String getTitle();
    String getAuthor();
    String getIsbn();
    String getRecommendedAges();
}
