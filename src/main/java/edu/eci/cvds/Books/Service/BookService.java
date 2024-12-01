package edu.eci.cvds.Books.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public interface BookService {

    String saveBook(BookRequest bookRequest);

    boolean deleteBook(String BookId);

    boolean updateBook(BookRequest bookRequest);

    Book getBook(String book);

    List<?> getAllBooks();
    String uploadImg(MultipartFile img,String isbn);
    List<Copy> getCopies(String BookId);
    List<ObjectNode> saveBooks(MultipartFile file);
}
