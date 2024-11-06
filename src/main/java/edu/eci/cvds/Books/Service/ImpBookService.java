package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.CopyDispo;
import edu.eci.cvds.Books.Domain.CopyState;
import edu.eci.cvds.Books.Exception.BookException;
import edu.eci.cvds.Books.Exception.CopyException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("Imp")
public class ImpBookService implements BookService{
    private final BRepository bookRepository;
    @Autowired
    public ImpBookService(@Qualifier("BookRepo") BRepository bookRepository){
        this.bookRepository=bookRepository;
    }

    @Override
    public boolean updateBook(Book book) {
        try {
            Book oldBook = (Book)bookRepository.BFindById(book.getId());
            if (oldBook == null) {
                throw new BookException(BookException.notFound);
            } else {
                if ( book.getISBN() == null || book.getTitle()==null || book.getAuthor()==null) {
                    throw new BookException(BookException.badBook);
                }
            }
            ((BookRepository) bookRepository).BUpdate(book);
            return true;
        } catch (IllegalArgumentException ex){
            throw new BookException(CopyException.badValues);
        }
    }

    @Override
    public void deleteBook(String BookId) {

    }

    @Override
    public void getBook(String book) {

    }

    @Override
    public void getAllBooks(String BookId) {

    }

    @Override
    public void saveBook(Book book){

        this.bookRepository.BSave(book);
    }


}
