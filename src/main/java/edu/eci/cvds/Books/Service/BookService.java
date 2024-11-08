package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface BookService {
    void saveBook(Book book);

    boolean deleteBook(String BookId);

    boolean updateBook(Book book);

    Book getBook(String book);

    void getAllBooks(String BookId);

    String uploadImg(MultipartFile img);

}
