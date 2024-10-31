package edu.eci.cvds.Books.Controller;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/BookModule")
public class BookController {

    private final BookService bookService;
    @Autowired
    public BookController(@Qualifier("Imp") BookService bookService){
        this.bookService=bookService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/SaveBook")
    public ResponseEntity<?> saveBook(@RequestBody Book book){
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
