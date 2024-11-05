package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BookRepo")
public interface BookRepository extends BRepository,JpaRepository<Book,String>{
    @Override
    default void BSave(Object book){
        save((Book)book);
    }

    @Override
    default void BDelete(Object object) {

    }

    @Override
    default Object BFindById(String id) {
        return null;
    }

    @Override
    default List<Book> BFindAll(){
        return findAll();
    }
}
