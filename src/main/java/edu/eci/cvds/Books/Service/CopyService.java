package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Exception.CopyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CopyService {
    boolean createCopy(String bookId, Copy copy) throws CopyException;
    boolean deleteCopy(Copy copy) throws CopyException;
    Copy getCopyById(String id) throws CopyException;
    List<?> findAllCopies();
    boolean updateCopies(Copy copy) throws CopyException;
    List<Copy> findCopiesByBook(Book book) throws CopyException;
    Copy findCopyByBarcode(String barcode) throws CopyException;
}
