package edu.eci.cvds.Books.Controller.ResponseModel;


import edu.eci.cvds.Books.Domain.Book;

import java.util.List;

public class BookResponse extends Response{
    List<Book> body;

    public List<Book> getBody() {
        return body;
    }

    public void setBody(List<Book> body) {
        this.body = body;
    }
}
