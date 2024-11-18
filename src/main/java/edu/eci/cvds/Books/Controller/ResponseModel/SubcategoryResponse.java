package edu.eci.cvds.Books.Controller.ResponseModel;

import edu.eci.cvds.Books.Domain.Subcategory;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class SubcategoryResponse extends Response<List<Subcategory>> {

    // Atributos finales con los mensajes
    public static final String SUCCESS_SUBCATEGORY_RETRIEVED = "Subcategories retrieved successfully";
    public static final String SUCCESS_SUBCATEGORY_SAVED = "Subcategory saved successfully";
    public static final String SUCCESS_SUBCATEGORY_UPDATED = "Subcategory updated successfully";
    public static final String SUCCESS_SUBCATEGORY_DELETED = "Subcategory deleted successfully";

    // Constructor principal
    public SubcategoryResponse(HttpStatus status, String message, List<Subcategory> body) {
        super(status, message, body);
    }

    // Constructor para una sola subcategoría (envuelta en una lista)
    public SubcategoryResponse(HttpStatus status, String message, Subcategory subcategory) {
        super(status, message, Collections.singletonList(subcategory));  // Envuelve la subcategoría en una lista
    }
}
