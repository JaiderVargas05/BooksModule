package edu.eci.cvds.Books.Controller.ResponseModel;

import edu.eci.cvds.Books.Domain.Subcategory;

import java.util.List;

public class SubcategoryResponse extends Response{
    List<Subcategory> body;

    public List<Subcategory> getBody() {
        return body;
    }

    public void setBody(List<Subcategory> body) {
        this.body = body;
    }
}
