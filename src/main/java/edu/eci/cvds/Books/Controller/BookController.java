package edu.eci.cvds.Books.Controller;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest){
        try{
//            // Crear el libro a partir del BookRequest
            Book book = new Book(bookRequest.getIsbn(), bookRequest.getDescription(), bookRequest.getTitle(),
                    bookRequest.getAuthor(), bookRequest.getEditorial(), bookRequest.getEdition(),
                    bookRequest.getYear());
//
//            // Guardar el libro con su categoría y subcategorías
            bookService.saveBook(book, bookRequest.getCategoryId(), bookRequest.getSubcategoryIds());
            //bookService.saveBook(book);
            return new ResponseEntity<>(book.getBookId(),HttpStatus.OK);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteBook")
    public ResponseEntity<?> deleteBook(@RequestParam String id){
        try{
            bookService.deleteBook(id);
            return new ResponseEntity<>("Book deleted successfully",HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody Book book){
        try{
            bookService.updateBook(book);
            return new ResponseEntity<>("Book updated successfully",HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getBook")
    public ResponseEntity<?> getBook(@RequestParam String id){
        try{
            Book book = bookService.getBook(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
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
        catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getCopies")
    public ResponseEntity<?> getTotalCopies(@RequestParam String bookId){
        try{
            List<Copy> copies = bookService.getCopies(bookId);
            return new ResponseEntity<>(copies, HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}