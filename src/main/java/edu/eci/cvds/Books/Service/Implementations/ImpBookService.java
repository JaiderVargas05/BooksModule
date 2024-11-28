package edu.eci.cvds.Books.Service.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Service.BookService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;

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
    public boolean updateBook(BookRequest bookRequest) {
        try {
            Book oldBook = (Book)bookRepository.BFindById(bookRequest.getBookId());
            if (oldBook == null) {
                throw new NotFoundException("Book", bookRequest.getBookId());
            }
            if (bookRequest.getIsbn() == null || bookRequest.getTitle() == null || bookRequest.getAuthor() == null) {
                throw new NotNullException("Book", "Required fields are missing");
            }
            if(bookRequest.getIsbn() == oldBook.getIsbn()){
                throw new BadRequestException("Book", bookRequest.getBookId());
            }
            Book book = new Book(bookRequest.getIsbn(), bookRequest.getDescription(), bookRequest.getTitle(),
                    bookRequest.getAuthor(), bookRequest.getEditorial(), bookRequest.getEdition(),bookRequest.getCollection(),
                    bookRequest.getRecommendedAges(), bookRequest.getLanguage());
            List<Category> categories = (List<Category>) categoryRepository.BFindAllById(bookRequest.getCategoryIds());
            if (categories.isEmpty()) {
                throw new NotFoundException("Categories", "none found for provided IDs");
            }
            book.setCategories(categories);
            List<Subcategory> subcategories = (List<Subcategory>) subcategoryRepository.BFindAllById(bookRequest.getSubcategoryIds());
            if (subcategories.isEmpty()) {
                throw new NotFoundException("Subcategories", "none found for provided IDs");
            }
            book.setSubcategories(subcategories);
            (bookRepository).BUpdate(book);
            return true;
        } catch (IllegalArgumentException ex){
            throw new BadValuesException("Invalid values for Book with ID " + bookRequest.getBookId());
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
        if(!book.isActive()){
            throw new BadRequestException("Book", id);
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
        List<Book> books = (List<Book>) bookRepository.BFindAll();
        List<Book> activeBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isActive()) {
                activeBooks.add(book);
            }
        }
        return activeBooks;

    }

    @Override
    public String saveBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest.getIsbn(), bookRequest.getDescription(), bookRequest.getTitle(),
                bookRequest.getAuthor(), bookRequest.getEditorial(), bookRequest.getEdition(),bookRequest.getCollection(),
                bookRequest.getRecommendedAges(), bookRequest.getLanguage());
        if (book == null){
            throw new NotNullException("Book","null");
        }
        if(book.getIsbn() == null){
            throw new BadObjectException("Book", book.getIsbn());
        }
        // Verificar si el ISBN ya está en uso
        Book existingBook =((BookRepository) bookRepository).findByIsbn(book.getIsbn());
        if (existingBook != null) {
            throw new BadObjectException("Book", "ISBN already exists: " + book.getIsbn());
        }
        List<Category> categories = (List<Category>) categoryRepository.BFindAllById(bookRequest.getCategoryIds());
        book.setCategories(categories);
        List<Subcategory> subcategories = (List<Subcategory>) subcategoryRepository.BFindAllById(bookRequest.getSubcategoryIds());
        book.setSubcategories(subcategories);
        bookRepository.BSave(book);
        String imgPath = this.uploadImg(bookRequest.getImg(), book.getBookId());
        book.setImgPath(imgPath);
        bookRepository.BSave(book);
        return book.getBookId();
    }
    @Override
    public List<Copy> getCopies(String bookId){
        if(bookId ==null || bookId.isEmpty()){
            throw new BadRequestException("Book",bookId);
        }
        Book book = this.getBook(bookId);
        if(book!=null && book.isActive()) {
            return book.getCopies();
        }
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
        List<Book> activeBooks = new ArrayList<>();
        for (Book bookItem : books) {
            if (bookItem.isActive()) {
                activeBooks.add(bookItem);
            }
        }
        if (activeBooks.isEmpty()) {
            throw new BadRequestException("Books by Author", bookId);
        }
        return activeBooks;
    }
    @Override
    public List<ObjectNode> saveBooks(MultipartFile file){
        List<ObjectNode> jsonList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Leer la primera hoja
            Row headerRow = sheet.getRow(0); // Primera fila como encabezados
            List<String> headers = new ArrayList<>();

            // Leer encabezados
            headerRow.forEach(cell -> headers.add(cell.getStringCellValue()));

            // Iterar sobre las filas de datos
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la primera fila de encabezados

                // Crear el objeto BookRequest
                BookRequest bookRequest = new BookRequest();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String header = headers.get(j); // Obtenemos el nombre del encabezado que es el nombre del campo

                    switch (cell.getCellType()) {
                        case STRING:
                            String stringValue = cell.getStringCellValue();
                            // Asignar el valor del campo en BookRequest basado en el encabezado
                            if ("isbn".equalsIgnoreCase(header)) {
                                bookRequest.setIsbn(stringValue);
                            } else if ("description".equalsIgnoreCase(header)) {
                                bookRequest.setDescription(stringValue);
                            } else if ("title".equalsIgnoreCase(header)) {
                                bookRequest.setTitle(stringValue);
                            } else if ("author".equalsIgnoreCase(header)) {
                                bookRequest.setAuthor(stringValue);
                            } else if ("editorial".equalsIgnoreCase(header)) {
                                bookRequest.setEditorial(stringValue);
                            } else if ("edition".equalsIgnoreCase(header)) {
                                bookRequest.setEdition(stringValue);
                            } else if ("collection".equalsIgnoreCase(header)) {
                                bookRequest.setCollection(stringValue);
                            } else if ("recommendedAges".equalsIgnoreCase(header)) {
                                bookRequest.setRecommendedAges(stringValue);
                            } else if ("language".equalsIgnoreCase(header)) {
                                bookRequest.setLanguage(stringValue);
                            } else if ("categoryIds".equalsIgnoreCase(header)) {
                                bookRequest.setCategoryIds(Arrays.asList(stringValue.split(",")));
                            } else if ("subcategoryIds".equalsIgnoreCase(header)) {
                                bookRequest.setSubcategoryIds(Arrays.asList(stringValue.split(",")));
                            }
                            break;
                        case NUMERIC:
                            double numericValue = cell.getNumericCellValue();

                            // Crear un DecimalFormat para formatear el número sin decimales
                            DecimalFormat decimalFormat = new DecimalFormat("#");
                            String numericString = decimalFormat.format(numericValue);
                            if ("edition".equalsIgnoreCase(header)) {
                                bookRequest.setEdition(numericString);
                            }
                            break;
                        case BOOLEAN:
                            boolean booleanValue = cell.getBooleanCellValue();
                            // Asignar el valor booleano si es necesario
                            break;
                        default:
                            break;
                    }
                }
                File imageFile = new File("src/main/resources/static/images/BookCover.jpg");

                // Crear un InputStream del archivo local
                FileInputStream fileInputStream = new FileInputStream(imageFile);

                // Crear un MockMultipartFile a partir de un archivo físico
                MockMultipartFile multipartFile = new MockMultipartFile(
                        "file", // Nombre del parámetro del archivo en la solicitud multipart
                        file.getName(), // Nombre del archivo
                        "application/octet-stream", // Tipo MIME del archivo (puedes cambiarlo según el archivo)
                        fileInputStream); // El flujo de entrada del archivo



                bookRequest.setImg(multipartFile);
                // Llamar al metodo saveBook con el BookRequest
                try{
                    String bookId = this.saveBook(bookRequest);
                } catch (Exception e) {
                    ObjectNode errorNode = objectMapper.createObjectNode();
                    errorNode.put("error", e.getMessage()); // Mensaje del error
                    errorNode.put("isbn", bookRequest.getIsbn());
                    errorNode.put("title", bookRequest.getTitle());
                    errorNode.put("author", bookRequest.getAuthor());
                    jsonList.add(errorNode);
                }

            }

        } catch (InvalidFormatException e) {
            throw new RuntimeException("El formato del archivo Excel no es válido", e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo Excel", e);
        } catch (Exception e) {
            throw new RuntimeException("Error procesando el archivo Excel", e);
        }

        return jsonList;
    }
}
