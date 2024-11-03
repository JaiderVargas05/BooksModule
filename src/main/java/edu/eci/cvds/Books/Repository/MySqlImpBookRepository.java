package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BMySql")
public interface MySqlImpBookRepository extends BookRepository,JpaRepository<Book,String>{
    @Override
    public default void saveBook(Book book){
        save(book);
    }
}
