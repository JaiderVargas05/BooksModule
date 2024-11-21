package edu.eci.cvds.Books.Service.Implementations;

import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service("Imp")
public class ImpBookService implements BookService {
    private final BRepository bookRepository;
    private final BRepository categoryRepository;
    private final BRepository subcategoryRepository;
    @Autowired
    public ImpBookService(@Qualifier("BookRepo") BRepository bookRepository,@Qualifier("CatRepo") BRepository categoryRepository,@Qualifier("SubRepo") BRepository subcategoryRepository){
        this.bookRepository=bookRepository;
        this.categoryRepository=categoryRepository;
        this.subcategoryRepository=subcategoryRepository;
    }

    @Override
    public boolean updateBook(Book book) {
        try {
            Book oldBook = (Book)bookRepository.BFindById(book.getBookId());
            if (oldBook == null) {
                throw new NotFoundException("Book", book.getBookId());
            }
            if (book.getIsbn() == null || book.getTitle() == null || book.getAuthor() == null) {
                throw new NotNullException("Book", "Required fields are missing");
            }
            (bookRepository).BUpdate(book);
            return true;
        } catch (IllegalArgumentException ex){
            throw new BadValuesException("Invalid values for Book with ID " + book.getBookId());
        }
    }


    @Override
    public boolean deleteBook(String BookId) {
        if (BookId == null || BookId == "" ){
            throw new NotNullException("Book ID", "null");
        } if (bookRepository.BFindById(BookId) == null){
            throw new NotFoundException("Book",BookId);
        }
        bookRepository.BDelete(BookId);
        return true;
    }

    @Override
    public Book getBook(String id) {
        if (id == null){
            throw new NotNullException("Book ID","null");
        }
        Book book = (Book)bookRepository.BFindById(id);
        if (book == null){
            throw new NotFoundException("Book", id);
        }
        return book;
    }
    @Override
    public String uploadImg(MultipartFile img,String bookId){
        try {
            if(img==null || bookId==null){
                throw new BadRequestException("Insufficient data");
            }
            String directoryPath = "src/main/resources/static/images/";
            String fileName = bookId+".jpg";
            Path filePath = Paths.get(directoryPath + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, img.getBytes());
            Book book = this.getBook(bookId);
            book.setImgPath("images/"+fileName);
            if(book==null){
                throw new NotFoundException("Book",bookId);
            }
            this.bookRepository.BSave(book);
            return "images/"+fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error saving the image.", e);
        }
    }

    @Override
    public List<?> getAllBooks() {
        return this.bookRepository.BFindAll();
    }

    @Override
    public String saveBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest.getIsbn(), bookRequest.getDescription(), bookRequest.getTitle(),
                bookRequest.getAuthor(), bookRequest.getEditorial(), bookRequest.getEdition(),
                bookRequest.getYear());
        if (book == null){
            throw new NotNullException("Book","null");
        }
        if(book.getIsbn() == null){
            throw new BadObjectException("Book", book.getIsbn());
        }
        Category category = (Category) categoryRepository.BFindById(bookRequest.getCategoryId());
        book.setCategory(category);
        List<Subcategory> subcategories = (List<Subcategory>) subcategoryRepository.BFindAllById(bookRequest.getSubcategoryIds());
        if (subcategories.isEmpty()) {
            throw new NotFoundException("Subcategories", "none found for provided IDs");
        }
        book.setSubcategories(subcategories);

        bookRepository.BSave(book);
        return book.getBookId();
    }
    @Override
    public List<Copy> getCopies(String bookId){
        Book book = this.getBook(bookId);
        if(book!=null)return book.getCopies();
        throw new NotFoundException("Book", bookId);
    }
    @Override
    public List<Book> findByAuthor(HashMap<String,String> book) {
        String bookId = book.get("bookId");
        String author = book.get("author");
        if (author == null || author == "" || bookId == null || bookId == "") {
            throw new NotNullException("Author","null");
        }
        List<Book> books = ((BookRepository) bookRepository).findBookByAuthor(bookId,author);
        if (books.isEmpty()){
            throw new NotFoundException("Book", author);
        }
        return books;
    }
}
