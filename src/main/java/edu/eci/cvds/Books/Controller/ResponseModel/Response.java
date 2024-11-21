package edu.eci.cvds.Books.Controller.ResponseModel;

import org.springframework.http.HttpStatus;

public abstract class Response {

    HttpStatus status;
    String message;
    Object body;

    protected Response(HttpStatus status, String message, Object body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}