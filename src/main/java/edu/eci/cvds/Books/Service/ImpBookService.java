package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("Imp")
public class ImpBookService implements BookService{
    private final BookRepository bookRepository;
    @Autowired
    public ImpBookService(@Qualifier("BMySql") BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    @Override
    public void saveBook(Book book){
        this.bookRepository.saveBook(book);
    }
}
