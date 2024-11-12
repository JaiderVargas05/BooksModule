package edu.eci.cvds.Books.Controller.ResponseModel;

import edu.eci.cvds.Books.Domain.Copy;

import java.util.List;

public class CopyResponse extends Response{
    List<Copy> body;

    public List<Copy> getBody() {
        return body;
    }

    public void setBody(List<Copy> body) {
        this.body = body;
    }
}
