package edu.eci.cvds.Books.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Controller.RequestModel.CopyRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface CopyService {
    String createCopy(String bookId, Copy copy);
    boolean deleteCopy(Copy copy);
    Copy getCopyById(String id) ;
    List<?> findAllCopies();
    boolean updateCopies(CopyRequest copyRequest);
    List<Copy> findCopiesByBook(Book book);
    Copy findCopyByBarcode(String barcode);

    List<ObjectNode> saveCopies(MultipartFile file);

    String createCopyByIsbn(CopyRequest copyRequest);
}
