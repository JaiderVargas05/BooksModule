package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Ejemplar;
import edu.eci.cvds.Books.Exception.EjemplarException;
import edu.eci.cvds.Books.Repository.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public interface EjemplarService {
    boolean createEjemplar(Ejemplar e) throws EjemplarException;
    boolean deleteEjemplar(Ejemplar e) throws EjemplarException;
    Ejemplar getEjemplarById(String id) throws EjemplarException;
    List<Ejemplar> findAllEjemplars();
    boolean updateEjemplar(Ejemplar e) throws EjemplarException;
    List<Ejemplar> findEjemplarsByBook(Book book) throws EjemplarException;
    Ejemplar findEjemplarByBarcode(Integer barcode) throws EjemplarException;
}
