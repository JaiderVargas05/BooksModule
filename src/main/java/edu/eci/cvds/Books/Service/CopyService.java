package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface CopyService {
    boolean createCopy(String bookId, Copy copy);
    boolean deleteCopy(Copy copy);
    Copy getCopyById(String id) ;
    List<?> findAllCopies();
    boolean updateCopies(Copy copy);
    List<Copy> findCopiesByBook(Book book);
    Copy findCopyByBarcode(String barcode);
}