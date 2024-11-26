package edu.eci.cvds.Books.Controller.ResponseModel;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CategoryResponse extends Response{
    public static final String SUCCESS_CATEGORY_SAVED = "Category saved successfully";
    public static final String SUCCESS_CATEGORY_RETRIEVED = "Category retrieved successfully";
    public static final String SUCCESS_CATEGORY_UPDATED = "Category updated successfully";
    public static final String SUCCESS_CATEGORY_DELETED = "Category deleted successfully";



    // Constructor para múltiples categorías
    public CategoryResponse(HttpStatus status, String message, List<?> body) {
        super(status, message, body);
    }

    // Constructor para una sola categoría
    public CategoryResponse(HttpStatus status, String message, Category category) {
        super(status, message, Collections.singletonList(category));  // Envuelve la categoría en una lista
    }

    public CategoryResponse(HttpStatus status, String message, String id) {
        super(status, message, id);
    }

    public CategoryResponse(HttpStatus status, String message, HashMap<String,List<Book>> categories) {
        super(status, message, categories);
    }
}
