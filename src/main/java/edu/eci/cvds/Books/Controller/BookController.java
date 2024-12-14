package edu.eci.cvds.Books.Controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Controller.ResponseModel.BookResponse;
import edu.eci.cvds.Books.Controller.ResponseModel.CopyResponse;
import edu.eci.cvds.Books.Controller.ResponseModel.Response;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Service.BookService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    public BookResponse saveBook(@RequestBody BookRequest bookRequest){
        try{
            String id = bookService.saveBook(bookRequest);
            return new BookResponse(HttpStatus.OK,BookResponse.SUCCESS_BOOK_SAVED,id);
        }catch (BadObjectException e){
            return new BookResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteBook")
    public BookResponse deleteBook(@RequestParam String id){
        try{
            bookService.deleteBook(id);
            return new BookResponse(HttpStatus.OK,BookResponse.SUCCESS_BOOK_DELETED,Collections.emptyList());
        }catch(NotFoundException e){
            return new BookResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new BookResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateBook")
    public BookResponse updateBook(@RequestBody BookRequest bookRequest){
        try{
            bookService.updateBook(bookRequest);
            return new BookResponse(HttpStatus.OK,BookResponse.SUCCESS_BOOK_UPDATED,bookRequest.getBookId());
        }catch(NotFoundException e){
            return new BookResponse(HttpStatus.NOT_FOUND, e.getMessage(), Collections.emptyList());
        } catch (BadRequestException e){
            return new BookResponse(HttpStatus.BAD_REQUEST,e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getBook")
    public BookResponse getBook(@RequestParam String id){
        try{
            Book book = bookService.getBook(id);
            return new BookResponse(HttpStatus.OK,BookResponse.SUCCESS_BOOK_RETRIEVED,book);
        }catch(NotFoundException e){
            return new BookResponse(HttpStatus.NOT_FOUND, e.getMessage(), Collections.emptyList());
        }catch (BadRequestException e){
            return new BookResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAllBooks")
    public BookResponse getAllBooks(){
        try{
            List<Book> books = (List<Book>) bookService.getAllBooks();
            return new BookResponse(HttpStatus.OK,BookResponse.SUCCESS_BOOK_RETRIEVED,books);
        }catch (ClassCastException e) {
            // Manejar el caso donde el casting falle
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error casting list to List<Book>", Collections.emptyList());
        }catch(NotFoundException e){
            return new BookResponse(HttpStatus.NOT_FOUND, e.getMessage(), Collections.emptyList());
        }catch (BadRequestException e){
            return new BookResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getCopies")
    public CopyResponse getTotalCopies(@RequestParam String bookId){
        try{
            List<Copy> copies = bookService.getCopies(bookId);
            return new CopyResponse(HttpStatus.OK, CopyResponse.SUCCESS_COPY_RETRIEVED,copies);
        }catch(NotFoundException e){
            return new CopyResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new CopyResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CopyResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/uploadImg")
    public ResponseEntity<?> uploadImage(@RequestParam String bookId,@RequestParam("file") MultipartFile img) {
        try{
            String path = this.bookService.uploadImg(img,bookId);
            return new ResponseEntity<>(path, HttpStatus.OK);
        }
        catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("*")
    @PostMapping("/saveBooks")
    public BookResponse saveBooks(@RequestParam("file") MultipartFile file) {
        try{
            List<ObjectNode> badBooks = bookService.saveBooks(file);
            return new BookResponse(HttpStatus.OK,BookResponse.SUCCESS_BOOK_SAVED,badBooks);

        }catch (BadRequestException e){
            return new BookResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());
        }
    }
}
