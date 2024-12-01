package edu.eci.cvds.Books.ServiceTest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.BadRequestException;
import edu.eci.cvds.Books.Exception.BadValuesException;
import edu.eci.cvds.Books.Exception.NotFoundException;
import edu.eci.cvds.Books.Exception.NotNullException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Repository.CategoryRepository;
import edu.eci.cvds.Books.Repository.Model.SearchBook;
import edu.eci.cvds.Books.Repository.SubcategoryRepository;
import edu.eci.cvds.Books.Service.Implementations.ImpBookService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class impBookServiceTest {


    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    private ImpBookService bookService;

    private BookRequest bookRequest;
    private Book book;
    private List<SearchBook> mockBooks;
    private String bookId;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks

        // Crear una instancia del servicio con los mocks
        bookService = new ImpBookService(bookRepository, categoryRepository, subcategoryRepository);

        // Inicialización de objetos de prueba
        bookRequest = new BookRequest();
        bookRequest.setBookId("1");
        bookRequest.setIsbn("123456");
        bookRequest.setTitle("Test Book 1");
        bookRequest.setAuthor("Test Author 1");
        bookRequest.setEditorial("Test Editorial 1");
        bookRequest.setEdition("1st Edition 1");
        bookRequest.setCollection("Test Collection1 ");
        bookRequest.setRecommendedAges("18+1");
        bookRequest.setLanguage("English1");
        // Mock para simular que el archivo se sube correctamente
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn("mockImageContent".getBytes());
        when(mockFile.isEmpty()).thenReturn(false);

        // Asignar el archivo mock al bookRequest
        bookRequest.setImg(mockFile);

        book = new Book("123456", "Test description", "Test Book", "Test Author", "Test Editorial", "1st Edition",
                "Test Collection", "18+", "English");
        book.setBookId("1");
        book.setActive(true);



        // Crear una lista de SearchBook simulada usando Mockito.mock
        SearchBook mockSearchBook1 = mock(SearchBook.class);
        when(mockSearchBook1.getBookId()).thenReturn("12345");
        when(mockSearchBook1.getTitle()).thenReturn("Test Book 1");
        when(mockSearchBook1.getAuthor()).thenReturn("Test Author 1");
        when(mockSearchBook1.getIsbn()).thenReturn("12345");
        when(mockSearchBook1.getRecommendedAges()).thenReturn("18+");
        when(mockSearchBook1.getImgPath()).thenReturn("images/12345.jpg");
        when(mockSearchBook1.getDescription()).thenReturn("Test description 1");

        SearchBook mockSearchBook2 = mock(SearchBook.class);
        when(mockSearchBook2.getBookId()).thenReturn("67890");
        when(mockSearchBook2.getTitle()).thenReturn("Test Book 2");
        when(mockSearchBook2.getAuthor()).thenReturn("Test Author 2");
        when(mockSearchBook2.getIsbn()).thenReturn("67890");
        when(mockSearchBook2.getRecommendedAges()).thenReturn("18+");
        when(mockSearchBook2.getImgPath()).thenReturn("images/67890.jpg");
        when(mockSearchBook2.getDescription()).thenReturn("Test description 2");

        // Listar los objetos mockeados
        mockBooks = Arrays.asList(mockSearchBook1, mockSearchBook2);

        // Configurar el repositorio para devolver la lista de SearchBook simulada cuando se llame a BFindAll
        when(bookRepository.BFindAll()).thenReturn(mockBooks);
        when(bookRepository.BFindById(anyString())).thenReturn(book);
        bookId = "1";

    }
    @Test
    void testSaveBook() throws IOException {
        String bookId = bookService.saveBook(bookRequest);
        assertNotNull(bookId);
        verify(bookRepository, times(3)).BSave(any(Book.class));
        verify(bookRequest.getImg(), times(1)).getBytes();
    }

    @Test
    void testUpdateBookSuccess() {
        boolean result = bookService.updateBook(bookRequest);
        assertTrue(result);
        verify(bookRepository, times(1)).BUpdate(any(Book.class));
    }

    @Test
    void testUpdateBookBookNotFound() {
        when(bookRepository.BFindById(anyString())).thenReturn(null);

        // Verificar que se lanza NotFoundException
        assertThrows(NotFoundException.class, () -> {
            bookService.updateBook(bookRequest);
        });
    }

    @Test
    void testUpdateBookBadRequestExceptionForDifferentISBN() {
        bookRequest.setIsbn("differentIsbn");
        when(bookRepository.BFindById(anyString())).thenReturn(book);

        // Verificar que se lanza BadRequestException
        assertThrows(BadRequestException.class, () -> {
            bookService.updateBook(bookRequest);
        });
    }

    @Test
    void testDeleteBook() {
        // Simular comportamiento del repositorio
        when(bookRepository.BFindById("1")).thenReturn(book);

        // Llamar al método a probar
        boolean result = bookService.deleteBook("1");

        // Verificar que el libro fue eliminado
        assertTrue(result);
        verify(bookRepository, times(1)).BDelete("1");
    }


    @Test
    void testDeleteBook_NullBookId() {
        assertThrows(NotNullException.class, () -> {
            bookService.deleteBook(null);
        });
    }

    @Test
    void testDeleteBookEmptyBookId() {
        assertThrows(NotNullException.class, () -> {
            bookService.deleteBook("");
        });
    }

    @Test
    void testGetBook_NullId() {
        assertThrows(NotNullException.class, () -> {
            bookService.getBook(null);
        });
    }

    @Test
    void testGetBookBookNotActive() {
        // Configurar el mock para devolver un libro no activo
        book.setActive(false);
        when(bookRepository.BFindById("1")).thenReturn(book);

        assertThrows(BadRequestException.class, () -> {
            bookService.getBook("1");
        });
    }

    @Test
    void testGetBookBookNotFound() {
        // Configurar el mock para devolver null cuando se busca el libro
        when(bookRepository.BFindById("1")).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            bookService.getBook("1");
        });
    }

    @Test
    void testUpdateBookCategoriesNotFound() {
        bookRequest.setCategoryIds(List.of("nonExistentCategory"));
        when(bookRepository.BFindById(anyString())).thenReturn(book);
        when(categoryRepository.BFindAllById(anyList())).thenReturn(Collections.emptyList());

        // Verificar que se lanza NotFoundException
        assertThrows(NotFoundException.class, () -> {
            bookService.updateBook(bookRequest);
        });
    }

    @Test
    void testUpdateBookSubcategoriesNotFound() {
        bookRequest.setSubcategoryIds(List.of("nonExistentSubcategory"));
        when(bookRepository.BFindById(anyString())).thenReturn(book);
        when(subcategoryRepository.BFindAllById(anyList())).thenReturn(Collections.emptyList());

        // Verificar que se lanza NotFoundException
        assertThrows(NotFoundException.class, () -> {
            bookService.updateBook(bookRequest);
        });
    }

    @Test
    void testDeleteBookNotFound() {
        // Configurar el mock para devolver null (libro no encontrado)
        when(bookRepository.BFindById("1")).thenReturn(null);

        // Ejecutar la prueba y verificar que se lanza una excepción NotFoundException
        assertThrows(NotFoundException.class, () -> bookService.deleteBook("1"));
    }

    @Test
    void testGetBookNotFound() {
        // Configurar el mock para devolver null (libro no encontrado)
        when(bookRepository.BFindById("1")).thenReturn(null);

        // Ejecutar la prueba y verificar que se lanza una excepción NotFoundException
        assertThrows(NotFoundException.class, () -> bookService.getBook("1"));
    }

    @Test
    void testUpdateBookBadValuesException() {
        when(bookRepository.BFindById(anyString())).thenReturn(book);
        doThrow(IllegalArgumentException.class).when(bookRepository).BUpdate(any(Book.class));

        // Verificar que se lanza BadValuesException
        assertThrows(BadValuesException.class, () -> {
            bookService.updateBook(bookRequest);
        });
    }


    @Test
    void testUploadImg() throws IOException {
        // Configurar el mock para retornar un libro
        when(bookRepository.BFindById("1")).thenReturn(book);

        // Mock para simular que el archivo se sube correctamente
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn("mockImageContent".getBytes());
        when(mockFile.isEmpty()).thenReturn(false);

        // Ejecutar el método a probar
        String imagePath = bookService.uploadImg(mockFile, "1");

        // Verificar el resultado
        assertEquals("images/1.jpg", imagePath);
        verify(bookRepository, times(1)).BSave(any(Book.class));
    }

    @Test
    void testUploadImgNullImg() {
        assertThrows(RuntimeException.class, () -> {
            bookService.uploadImg(null, "1");
        });
    }

    @Test
    void testUploadImgNullBookId() {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "mockImageContent".getBytes());
        assertThrows(RuntimeException.class, () -> {
            bookService.uploadImg(mockFile, null);
        });
    }

    @Test
    void testUploadImgBookNotFound() {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "mockImageContent".getBytes());
        when(bookRepository.BFindById("1")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            bookService.uploadImg(mockFile, "1");
        });
    }
    @Test
    void testGetAllBooks() {
        // Llamar al método a probar
        List<?> books = bookService.getAllBooks();
        // Verificar que el repositorio ha sido llamado y la lista devuelta es correcta
        verify(bookRepository, times(1)).BFindAll(); // Verifica que se llama una vez
        assertEquals(mockBooks.size(), books.size()); // Verifica que la lista devuelta tiene el tamaño esperado
    }

    // Prueba cuando bookId es null o vacío
    @Test
    void testGetCopies_BookIdNull() {
        assertThrows(BadRequestException.class, () -> {
            bookService.getCopies(null);  // Pasando bookId como null
        });
    }

    @Test
    void testGetCopies_BookIdEmpty() {
        assertThrows(BadRequestException.class, () -> {
            bookService.getCopies("");  // Pasando bookId como vacío
        });
    }

    // Prueba cuando no se encuentra el libro
    @Test
    void testGetCopies_BookNotFound() {
        // Simula que el libro no se encuentra
        when(bookRepository.BFindById(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            bookService.getCopies(bookId);  // Buscando un libro que no existe
        });
    }


    // Prueba cuando el libro es válido y tiene copias
    @Test
    void testGetCopies_ValidBookWithCopies() {
        // Crear un libro activo
        Book activeBook = mock(Book.class);
        when(activeBook.isActive()).thenReturn(true);

        // Crear copias para el libro
        Copy copy1 = new Copy(activeBook, "GOOD_CONDITION");
        Copy copy2 = new Copy(activeBook, "DAMAGED");
        List<Copy> copies = Arrays.asList(copy1, copy2);

        when(bookRepository.BFindById(anyString())).thenReturn(activeBook);
        when(activeBook.getCopies()).thenReturn(copies);

        // Ejecutar el método y verificar el resultado
        List<Copy> result = bookService.getCopies(bookId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(copy1));
        assertTrue(result.contains(copy2));
    }


    @Test
    void saveBooks_success() throws Exception {
        // Crear un libro de prueba en formato Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("isbn");
        headerRow.createCell(1).setCellValue("title");
        headerRow.createCell(2).setCellValue("author");
        headerRow.createCell(3).setCellValue("editorial");
        headerRow.createCell(4).setCellValue("edition");
        headerRow.createCell(5).setCellValue("collection");
        headerRow.createCell(6).setCellValue("recommendedAges");
        headerRow.createCell(7).setCellValue("language");
        headerRow.createCell(8).setCellValue("categoryIds");
        headerRow.createCell(9).setCellValue("subcategoryIds");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("1234567890");
        dataRow.createCell(1).setCellValue("Test Title");
        dataRow.createCell(2).setCellValue("Test Author");
        dataRow.createCell(3).setCellValue("Test Editorial");
        dataRow.createCell(4).setCellValue("First Edition");
        dataRow.createCell(5).setCellValue("Fantasy");
        dataRow.createCell(6).setCellValue("12,13");
        dataRow.createCell(7).setCellValue("English");
        dataRow.createCell(8).setCellValue("1,2");
        dataRow.createCell(9).setCellValue("10,11");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        MockMultipartFile file = new MockMultipartFile("file", "books.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new ByteArrayInputStream(outputStream.toByteArray()));

        when(bookRepository.findByIsbn("1234567890")).thenReturn(null);

        // Ejecutar el método
        List<ObjectNode> result = bookService.saveBooks(file);

        assertTrue(result.isEmpty());

        // Verificar que BSave haya sido invocado
        verify(bookRepository, times(3)).BSave(any(Book.class));
    }

    @Test
    void saveBooks_invalidFormat() {
        MockMultipartFile file = new MockMultipartFile("file", "invalid.txt", "text/plain", "Invalid content".getBytes());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.saveBooks(file);
        });
        assertTrue(exception.getMessage().contains("Error al leer el archivo Excel"));

    }
    @Test
    void saveBooks_existingIsbn1() throws Exception {
        // Crear un libro de prueba en formato Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row headerRow = sheet.createRow(0);

        // Crear las celdas de encabezado
        headerRow.createCell(0).setCellValue("isbn");
        headerRow.createCell(1).setCellValue("title");
        headerRow.createCell(2).setCellValue("author");
        headerRow.createCell(3).setCellValue("editorial");
        headerRow.createCell(4).setCellValue("edition");
        headerRow.createCell(5).setCellValue("collection");
        headerRow.createCell(6).setCellValue("recommendedAges");
        headerRow.createCell(7).setCellValue("language");
        headerRow.createCell(8).setCellValue("categoryIds");
        headerRow.createCell(9).setCellValue("subcategoryIds");

        // Crear la fila de datos
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("1234567890");
        dataRow.createCell(1).setCellValue("Test Title");
        dataRow.createCell(2).setCellValue("Test Author");
        dataRow.createCell(3).setCellValue("Test Editorial");
        dataRow.createCell(4).setCellValue("First Edition");
        dataRow.createCell(5).setCellValue("Fantasy");
        dataRow.createCell(6).setCellValue("12,13");
        dataRow.createCell(7).setCellValue("English");
        dataRow.createCell(8).setCellValue("1,2");
        dataRow.createCell(9).setCellValue("10,11");

        // Crear un ByteArrayOutputStream para escribir el libro
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Crear el MockMultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "books.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new ByteArrayInputStream(outputStream.toByteArray()));

        // Mockear las respuestas de los repositorios
        when(bookRepository.findByIsbn("1234567890")).thenReturn(new Book());

        // Ejecutar el método
        List<ObjectNode> result = bookService.saveBooks(file);

        // Verificar resultados
        assertFalse(result.isEmpty());
        ObjectNode errorNode = result.get(0);
        assertEquals("Book with  ISBN already exists: 1234567890 is incomplete data.", errorNode.get("error").asText());

        // Verificar que el libro no fue guardado
        verify(bookRepository, never()).save(any(Book.class));
    }
}
