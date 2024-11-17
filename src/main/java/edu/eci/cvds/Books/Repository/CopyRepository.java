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
    @Override
    default void BUpdate(Object copy) {
        this.save((Copy)copy);
    }
    default void BDelete(String id) {
        Copy copy = findById(id).orElse(null);
        copy.setActive(false);
        this.BSave(copy);
    }
    default Copy BFindById(String id) {
        return this.findById(id).orElse(null);
    }
    Copy findCopyByBarCode(String barCode);
    default List<Copy> BFindAll(){
        return findAll();
    }
    List<Copy> findCopyByBook(Book book);
    @Override
    public default List<?> BFindAllById(List<String> Ids){
        return findAllById(Ids);
    }

}
