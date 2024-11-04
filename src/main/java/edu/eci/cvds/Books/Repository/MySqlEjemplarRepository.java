package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Ejemplar;
import edu.eci.cvds.Books.Domain.EjemplarDispo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EjRepSql")
public interface MySqlEjemplarRepository extends EjemplarRepository,JpaRepository<Ejemplar, String> {
    default void createEjemplar(Ejemplar ejemplar) {
        this.save(ejemplar);
    }
    default void updateEjemplar(Ejemplar ejemplar) {
        this.save(ejemplar);
    }
    default void deleteEjemplar(Ejemplar ejemplar) {
        ejemplar.setActive(false);
        this.save(ejemplar);
    }
    default Ejemplar findEjemplarById(String id) {
        return this.findById(id).orElse(null);
    }
    Ejemplar findEjemplarByBarCode(Integer barCode);
    List<Ejemplar> findAllEjemplars();
    List<Ejemplar> findEjemplarByBook(Book book);
}
