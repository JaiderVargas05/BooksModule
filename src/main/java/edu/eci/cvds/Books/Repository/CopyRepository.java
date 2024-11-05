package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EjRepSql")
public interface CopyRepository extends BRepository,JpaRepository<Copy, String> {
    @Override
    default void BSave(Object copy) {
        this.save((Copy)copy);
    }

    default void updateEjemplar(Copy copy) {
        this.save(copy);
    }
    default void BDelete(Object copy) {
        ((Copy)copy).setActive(false);
        this.save((Copy)copy);
    }
    default Copy BFindById(String id) {
        return this.findById(id).orElse(null);
    }
    Copy findEjemplarByBarCode(String barCode);
    default List<Copy> BFindAll(){
        return findAll();
    }
    List<Copy> findEjemplarByBook(Book book);

}
