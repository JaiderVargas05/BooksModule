package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    void saveBook(Book book);

}
