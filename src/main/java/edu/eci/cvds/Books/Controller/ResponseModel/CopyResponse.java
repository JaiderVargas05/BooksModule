package edu.eci.cvds.Books.Controller.ResponseModel;

import edu.eci.cvds.Books.Domain.Copy;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class CopyResponse extends Response<Object> {

    // Atributos finales con los mensajes
    public static final String SUCCESS_COPY_RETRIEVED = "Copies retrieved successfully";
    public static final String SUCCESS_COPY_SAVED = "Copy saved successfully";
    public static final String SUCCESS_COPY_UPDATED = "Copy updated successfully";
    public static final String SUCCESS_COPY_DELETED = "Copy deleted successfully";

    // Constructor principal
    public CopyResponse(HttpStatus status, String message, List<Copy> body) {
        super(status, message, body);
    }

    // Constructor para una sola copia (envuelta en una lista)
    public CopyResponse(HttpStatus status, String message, Copy copy) {
        super(status, message, Collections.singletonList(copy));  // Envuelve la copia en una lista
    }

    public CopyResponse(HttpStatus status, String message, String id) {
        super(status, message, id);
    }
}
