package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Exception.BookException;
import edu.eci.cvds.Books.Exception.CopyException;
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
    public boolean updateBook(Book book) {
        try {
            Book oldBook = (Book)bookRepository.BFindById(book.getBookId());
            if (oldBook == null) {
                throw new BookException(BookException.notFound);
            } else {
                if ( book.getIsbn() == null || book.getTitle()==null || book.getAuthor()==null) {
                    throw new BookException(BookException.badBook);
                }
            }
            (bookRepository).BUpdate(book);
            return true;
        } catch (IllegalArgumentException ex){
            throw new BookException(CopyException.badValues);
        }
    }

    @Override
    public boolean deleteBook(String BookId) {
        if (BookId == null || BookId == "" ){
            throw new BookException(BookException.notNull);
        } if (bookRepository.BFindById(BookId) == null){
            throw new BookException(BookException.notFound);
        }
        bookRepository.BDelete(BookId);
        return true;
    }

    @Override
    public Book getBook(String id) {
        if (id == null){
            throw new BookException(BookException.dataNotNull);
        }
        Book book = (Book)bookRepository.BFindById(id);
        if (book == null){
            throw new BookException(BookException.notFound);
        }
        return book;
    }

    @Override
    public void getAllBooks(String BookId) {
        this.bookRepository.BFindAll();
    }

    @Override
    public void saveBook(Book book) throws BookException{
        if (book == null){
            throw new BookException(BookException.notNull);
        }
        if(book.getIsbn() == null){
            throw new BookException(BookException.badBook);
        }
        this.bookRepository.BSave(book);
    }


}
