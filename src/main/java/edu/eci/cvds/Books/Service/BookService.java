package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    void saveBook(Book book);
}
