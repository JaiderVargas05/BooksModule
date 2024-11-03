package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {
    public void saveBook(Book book);
}
