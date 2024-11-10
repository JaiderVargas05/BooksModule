package edu.eci.cvds.Books.Service.Implementations;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Exception.BookException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.List;

@Service("Imp")
public class ImpBookService implements BookService {
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
    public void saveBook(Book book) throws BookException{
        if (book == null){
            throw new BookException(BookException.notNull);
        }
        if(book.getIsbn() == null){
            throw new BookException(BookException.badBook);
        }
        this.bookRepository.BSave(book);
//        // Buscar y asignar la categoría
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new BookException("Category not found"));
//        book.setCategory(category);
//
//        // Buscar y asignar las subcategorías
//        List<Subcategory> subcategories = subcategoryRepository.findAllById(subcategoryIds);
//        if (subcategories.isEmpty()) {
//            throw new BookException("Subcategories not found");
//        }
//        book.setSubcategories(subcategories);
//
//        // Guardar el libro en la base de datos
//        bookRepository.save(book);
    }


}