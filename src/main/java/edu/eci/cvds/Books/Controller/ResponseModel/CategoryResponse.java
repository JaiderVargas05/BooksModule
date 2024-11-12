package edu.eci.cvds.Books.Controller.ResponseModel;

import edu.eci.cvds.Books.Domain.Category;

import java.util.List;

public class CategoryResponse extends Response{
    List<Category> body;

    public List<Category> getBody() {
        return body;
    }

    public void setBody(List<Category> body) {
        this.body = body;
    }
}
