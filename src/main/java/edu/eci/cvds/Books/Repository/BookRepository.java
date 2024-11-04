package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BookRepo")
public interface BookRepository extends BRepository,JpaRepository<Book,String>{
    @Override
    public default void BSave(Object book){
        BSave((Book)book);
    }
}
