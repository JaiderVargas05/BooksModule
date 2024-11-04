package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Repository.BRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("Imp")
public class ImpBookService implements BookService{
    private final BRepository bookRepository;
    @Autowired
    public ImpBookService(@Qualifier("BookRepo") BRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    @Override
    public void saveBook(Object book){
        this.bookRepository.BSave(book);
    }
}
