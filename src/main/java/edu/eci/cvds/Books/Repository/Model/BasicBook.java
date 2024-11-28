package edu.eci.cvds.Books.Repository.Model;

import java.util.List;

public interface BasicBook {
    String getBookId();
    String getTitle();
    String getAuthor();
    String getIsbn();
    String getRecommendedAges();
    String getImgPath();

    //List<String> getCategories();
}
