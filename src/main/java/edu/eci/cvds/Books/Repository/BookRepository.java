package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("BookRepo")
public interface BookRepository extends BRepository,JpaRepository<Book,String>{
    @Override
    default void BSave(Object book){
        save((Book)book);
    }

    @Override
    default void BDelete(String id) {
        Book book = findById(id).orElse(null);
        book.setActive(false);
        this.BSave(book);

    }

    @Override
    default void BUpdate(Object book){
        save((Book) book);
    }
    @Override
    default Object BFindById(String id) {
        return findById(id).orElse(null);
    }
    @Override
    default List<Book> BFindAll(){
        return findAll();
    }
    @Override
    public default List<?> BFindAllById(List<String> Ids){
        return findAllById(Ids);
    }
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.bookId != :bookId")
    List<Book> findBookByAuthor(String bookId, String author);
    Book findByIsbn(String isbn);

}
