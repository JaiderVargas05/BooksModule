package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BookService {
    void saveBook(Book book);

    boolean deleteBook(String BookId);

    boolean updateBook(Book book);

    Book getBook(String book);

    List<?> getAllBooks(String BookId);

    String uploadImg(MultipartFile img);

    List<Copy> getCopies(String BookId);

}
