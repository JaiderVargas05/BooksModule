package edu.eci.cvds.Books.Controller;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Exception.BookException;
import edu.eci.cvds.Books.Exception.CopyException;
import edu.eci.cvds.Books.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/BookModule")
public class BookController {

    private final BookService bookService;
    @Autowired
    public BookController(@Qualifier("Imp") BookService bookService){
        this.bookService=bookService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/saveBook")
    public ResponseEntity<?> saveBook(@RequestBody Book book){
        try{
            bookService.saveBook(book);
            return new ResponseEntity<>(book.getBookId(),HttpStatus.OK);
        }catch (BookException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteBook")
    public ResponseEntity<?> deleteCopy(@RequestParam String id){
        try{
            bookService.deleteBook(id);
            return new ResponseEntity<>("Book deleted successfully",HttpStatus.OK);
        }catch (BookException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody Book book){
        try{
            bookService.updateBook(book);
            return new ResponseEntity<>("Book updated successfully",HttpStatus.OK);
        } catch (BookException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getBook")
    public ResponseEntity<?> getBook(@RequestParam String id){
        try{
            Book book = bookService.getBook(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }catch (BookException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/uploadImg")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile img) {
        try{
            String path = this.bookService.uploadImg(img);
            return new ResponseEntity<>(path, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
