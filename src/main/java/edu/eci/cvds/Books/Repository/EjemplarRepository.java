package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Ejemplar;
import edu.eci.cvds.Books.Domain.EjemplarDispo;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface EjemplarRepository {
    void createEjemplar(Ejemplar ejemplar);
    void updateEjemplar(Ejemplar ejemplar);
    void deleteEjemplar(Ejemplar ejemplar);
    Ejemplar findEjemplarById(String id);
    Ejemplar findEjemplarByBarCode(Integer barCode);
    List<Ejemplar> findAllEjemplars();
    List<Ejemplar> findEjemplarByBook(Book book);
}
