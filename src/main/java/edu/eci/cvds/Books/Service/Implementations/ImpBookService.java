package edu.eci.cvds.Books.Service.Implementations;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.BookException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;

@Service("Imp")
public class ImpBookService implements BookService {
    private final BRepository bookRepository;
    private final BRepository categoryRepository;
    private final BRepository subcategoryRepository;
    @Autowired
    public ImpBookService(@Qualifier("BookRepo") BRepository bookRepository,@Qualifier("CatRepo") BRepository subcategoryRepository,@Qualifier("SubRepo") BRepository categoryRepository){
        this.bookRepository=bookRepository;
        this.categoryRepository=categoryRepository;
        this.subcategoryRepository=subcategoryRepository;
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
            throw new BookException(BookException.badValues);
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
    public String uploadImg(MultipartFile img){
        try {
            String directoryPath = "images/";
            String fileName = img.getOriginalFilename();
            Path filePath = Paths.get(directoryPath + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, img.getBytes());
            return filePath.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen.", e);
        }
    }

    @Override
    public List<?> getAllBooks(String BookId) {
        return this.bookRepository.BFindAll();
    }

    @Override
    public void saveBook(Book book,String categoryId, List<String> subcategoryIds) throws BookException{
        if (book == null){
            throw new BookException(BookException.notNull);
        }
        if(book.getIsbn() == null){
            throw new BookException(BookException.badBook);
        }
        this.bookRepository.BSave(book);
        Category category = (Category) categoryRepository.BFindById(categoryId);
        book.setCategory(category);
        List<Subcategory> subcategories = (List<Subcategory>) (List<?>) subcategoryRepository.BFindAllById(subcategoryIds);
        if (subcategories.isEmpty()) {
            throw new BookException("Subcategories not found");
        }
        book.setSubcategories(subcategories);
        bookRepository.BSave(book);
    }
    @Override
    public List<Copy> getCopies(String bookId){
        Book book = this.getBook(bookId);
        if(book!=null)return book.getCopies();
        throw new BookException(BookException.notFound);
    }


}
