package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("CopyRep")
public interface CopyRepository extends BRepository,JpaRepository<Copy, String> {
    @Override
    default void BSave(Object copy) {
        this.save((Copy)copy);
    }

    default void updateCopy(Copy copy) {
        this.save(copy);
    }
    default void BDelete(Object copy) {
        ((Copy)copy).setActive(false);
        this.save((Copy)copy);
    }
    default Copy BFindById(String id) {
        return this.findById(id).orElse(null);
    }
    Copy findCopyByBarCode(String barCode);
    default List<Copy> BFindAll(){
        return findAll();
    }
    List<Copy> findCopyByBook(Book book);
    @Query("SELECT b FROM Book b WHERE b.book_id = :book_id")
    Book findBookById(@Param("book_id") String book_id);

}
