package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    void saveBook(Book book);

    void deleteBook(String BookId);

    boolean updateBook(Book book);

    void getBook(String book);

    void getAllBooks(String BookId);

}
