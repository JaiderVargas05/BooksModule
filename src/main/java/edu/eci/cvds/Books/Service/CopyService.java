package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Exception.CopyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CopyService {
    boolean createEjemplar(Copy e) throws CopyException;
    boolean deleteEjemplar(Copy e) throws CopyException;
    Copy getEjemplarById(String id) throws CopyException;
    List<?> findAllEjemplars();
    boolean updateEjemplar(Copy e) throws CopyException;
    List<Copy> findEjemplarsByBook(Book book) throws CopyException;
    Copy findEjemplarByBarcode(String barcode) throws CopyException;
}
