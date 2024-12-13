package edu.eci.cvds.Books.implementations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Codes.CodeGenerator;
import edu.eci.cvds.Books.Codes.GenerateCodeException;
import edu.eci.cvds.Books.Controller.RequestModel.CopyRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.CopyDispo;
import edu.eci.cvds.Books.Domain.CopyState;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Repository.CopyRepository;
import edu.eci.cvds.Books.Service.CopyService;
import edu.eci.cvds.Books.Service.Implementations.ImpCopyService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.TransientObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImpCopyServiceTest {

    @Mock
    private CopyRepository copyRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CodeGenerator codeGenerator;

    private ImpCopyService copyService;

    private CopyRequest copyRequest;
    private Copy existingCopy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        copyService = new ImpCopyService(copyRepository, bookRepository, codeGenerator);
        // Inicializando CopyRequest
        copyRequest = new CopyRequest();
        copyRequest.setId("1");
        copyRequest.setIsbn("1234567890");
        copyRequest.setState("Good");
        copyRequest.setDisponibility(CopyDispo.AVAILABLE);
        copyRequest.setUbication("shelf1");

        // Inicializando Copy
        existingCopy = new Copy();
        existingCopy.setId("1");
        existingCopy.setState("Fair");
        existingCopy.setDisponibility(CopyDispo.BORROWED);
    }

    @Test
    void createCopy_whenValidCopy_createsAndReturnsId() throws Exception {
        // Arrange
        Copy copy = new Copy();
        copy.setId("1");
        copy.setState("GOOD_CONDITION");

        Book book = new Book();
        book.setBookId("book1");

        when(bookRepository.BFindById("book1")).thenReturn(book);
        when(codeGenerator.generateCode("1")).thenReturn("barcode123");
        doNothing().when(copyRepository).BSave(any(Copy.class));

        // Act
        String copyId = copyService.createCopy("book1", copy);

        // Assert
        assertEquals("1", copyId);
        assertEquals("barcode123", copy.getBarCode());
        verify(copyRepository, times(2)).BSave(any(Copy.class));
    }

    @Test
    void createCopy_whenNullCopy_throwsNotNullException() {
        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            copyService.createCopy("book1", null);
        });
        assertEquals("Copy must not be null", exception.getMessage());
    }

    @Test
    void createCopy_whenNullState_throwsBadStateException() {
        // Arrange
        Copy copy = new Copy();
        copy.setId("1");

        // Act & Assert
        BadStateException exception = assertThrows(BadStateException.class, () -> {
            copyService.createCopy("book1", copy);
        });
        assertEquals("Copy with ID: 1 contains invalid state.", exception.getMessage());
    }

    @Test
    void createCopy_whenBookNotFound_throwsNotFoundException() {
        // Arrange
        Copy copy = new Copy();
        copy.setId("1");
        copy.setState("GOOD_CONDITION");

        when(bookRepository.BFindById("book1")).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            copyService.createCopy("book1", copy);
        });
        assertEquals("Copy with ID: book1 was not found.", exception.getMessage());
    }

    @Test
    void createCopy_whenGenerateCodeFails_throwsBadObjectException() throws Exception {
        // Arrange
        Copy copy = new Copy();
        copy.setId("1");
        copy.setState("GOOD_CONDITION");

        Book book = new Book();
        book.setBookId("book1");

        when(bookRepository.BFindById("book1")).thenReturn(book);
        when(codeGenerator.generateCode("1")).thenThrow(new GenerateCodeException("Error generating code"));

        // Act & Assert
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            copyService.createCopy("book1", copy);
        });
        assertEquals("Copy with book1 is incomplete data.", exception.getMessage());
    }
    @Test
    void createCopy_whenIllegalArgumentExceptionThrown_throwsBadStateException() {
        // Arrange
        Copy copy = new Copy();
        copy.setId("1");
        copy.setState("GOOD_CONDITION");

        Book book = new Book();
        book.setBookId("book1");

        when(bookRepository.BFindById("book1")).thenReturn(book);
        doThrow(new IllegalArgumentException("Invalid argument")).when(copyRepository).BSave(any(Copy.class));

        // Act & Assert
        BadStateException exception = assertThrows(BadStateException.class, () -> {
            copyService.createCopy("book1", copy);
        });
        assertEquals("Copy with ID: 1 contains invalid state.", exception.getMessage());
    }


    @Test
    void createCopyByIsbn_whenIsbnIsNull_throwsNotNullException() {
        // Arrange
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setIsbn(null);
        copyRequest.setState("Good");

        // Act & Assert
        assertThrows(NotNullException.class, () -> copyService.createCopyByIsbn(copyRequest));
    }

    @Test
    void createCopyByIsbn_whenStateIsNull_throwsBadStateException() {
        // Arrange
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setIsbn("12345");
        copyRequest.setState(null);

        // Act & Assert
        assertThrows(BadStateException.class, () -> copyService.createCopyByIsbn(copyRequest));
    }

    @Test
    void createCopyByIsbn_whenBookNotFound_throwsNotFoundException() {
        // Arrange
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setIsbn("12345");
        copyRequest.setState("Good");

        when(bookRepository.findByIsbn("12345")).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> copyService.createCopyByIsbn(copyRequest));
    }

    @Test
    void createCopyByIsbn_whenCopyIsCreated_returnsCopyId() throws GenerateCodeException {
        // Arrange
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setIsbn("12345");
        copyRequest.setState("Good");
        copyRequest.setUbication("Shelf A");

        Book book = new Book();
        book.setIsbn("12345");

        Copy copy = new Copy(book, "Good", "Shelf A");

        when(bookRepository.findByIsbn("12345")).thenReturn(book);
        doAnswer(invocation -> {
            Copy argCopy = invocation.getArgument(0);
            argCopy.setId("1");  // Simular la generación del ID en la base de datos
            return null;
        }).when(copyRepository).BSave(any(Copy.class));
        when(codeGenerator.generateCode("1")).thenReturn("ABC123");

        // Act
        String copyId = copyService.createCopyByIsbn(copyRequest);

        // Assert
        assertEquals("1", copyId);

        // Verificar que el método BSave se llamó dos veces
        verify(copyRepository, times(2)).BSave(any(Copy.class));

        // Verificar el código de barras en el objeto actualizado
        ArgumentCaptor<Copy> copyCaptor = ArgumentCaptor.forClass(Copy.class);
        verify(copyRepository, times(2)).BSave(copyCaptor.capture());
        Copy savedCopy = copyCaptor.getAllValues().get(1);  // Obtener el valor guardado en la segunda llamada
        assertEquals("ABC123", savedCopy.getBarCode());
    }



    @Test
    void createCopyByIsbn_whenIllegalArgumentExceptionThrown_throwsBadStateException() {
        // Arrange
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setIsbn("12345");
        copyRequest.setState("Good");

        Book book = new Book();
        book.setIsbn("12345");

        when(bookRepository.findByIsbn("12345")).thenReturn(book);
        doThrow(IllegalArgumentException.class).when(copyRepository).BSave(any(Copy.class));

        // Act & Assert
        assertThrows(BadStateException.class, () -> copyService.createCopyByIsbn(copyRequest));
    }

    @Test
    void createCopyByIsbn_whenTransientObjectExceptionThrown_throwsBadObjectException() {
        // Arrange
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setIsbn("12345");
        copyRequest.setState("Good");
        copyRequest.setUbication("Shelf A");

        Book book = new Book();
        book.setIsbn("12345");

        when(bookRepository.findByIsbn("12345")).thenReturn(book);
        doThrow(new TransientObjectException("error")).when(copyRepository).BSave(any(Copy.class));

        // Act & Assert
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            copyService.createCopyByIsbn(copyRequest);
        });

        assertEquals( "Copy with 12345 is incomplete data.",exception.getMessage());
    }

    @Test
    void createCopyByIsbn_whenGenerateCodeExceptionThrown_throwsBadObjectException() throws GenerateCodeException {
        // Arrange
        CopyRequest copyRequest = new CopyRequest();
        copyRequest.setIsbn("12345");
        copyRequest.setState("Good");
        copyRequest.setUbication("Shelf A");

        Book book = new Book();
        book.setIsbn("12345");

        Copy copy = new Copy(book, "Good", "Shelf A");
        copy.setId("1");

        when(bookRepository.findByIsbn("12345")).thenReturn(book);
        doAnswer(invocation -> {
            Copy argCopy = invocation.getArgument(0);
            argCopy.setId("1");  // Simular la generación del ID en la base de datos
            return null;
        }).when(copyRepository).BSave(any(Copy.class));
        doThrow(new GenerateCodeException(GenerateCodeException.CODE_GENERATION_FAILED)).when(codeGenerator).generateCode("1");

        // Act & Assert
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            copyService.createCopyByIsbn(copyRequest);
        });

        assertEquals("Copy with 12345 is incomplete data.", exception.getMessage());
    }

    //delete

    @Test
    void deleteCopy_whenIdCopyIsNull_throwsNotNullException() {
        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            copyService.deleteCopy(null);
        });

        assertEquals("Copy must not be null", exception.getMessage());
    }

    @Test
    void deleteCopy_whenCopyNotFound_throwsNotFoundException() {
        // Arrange
        String idCopy = "12345";
        when(copyRepository.BFindById(idCopy)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            copyService.deleteCopy(idCopy);
        });

        assertEquals("Copy with ID: 12345 was not found.", exception.getMessage());
    }

    @Test
    void deleteCopy_whenCopyExists_deletesCopyAndReturnsTrue() {
        // Arrange
        String idCopy = "12345";
        Copy copy = new Copy();
        when(copyRepository.BFindById(idCopy)).thenReturn(copy);

        // Act
        boolean result = copyService.deleteCopy(idCopy);

        // Assert
        assertTrue(result);
        verify(copyRepository, times(1)).BDelete(idCopy);
    }
    //getCopyById
    @Test
    void getCopyById_whenIdIsNull_throwsNotNullException() {
        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            copyService.getCopyById(null);
        });

        assertEquals("Copy must not be null", exception.getMessage());
    }

    @Test
    void getCopyById_whenIdIsInvalid_throwsBadFormatException() {
        // Arrange
        String invalidId = "1234";

        // Act & Assert
        BadFormatException exception = assertThrows(BadFormatException.class, () -> {
            copyService.getCopyById(invalidId);
        });

        assertEquals("Copy ID has a bad format: 1234", exception.getMessage());
    }

    @Test
    void getCopyById_whenCopyNotFound_throwsNotFoundException() {
        // Arrange
        String validId = "123e4567-e89b-12d3-a456-426614174000";

        when(copyRepository.BFindById(validId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            copyService.getCopyById(validId);
        });

        assertEquals("Copy with ID: 123e4567-e89b-12d3-a456-426614174000 was not found.", exception.getMessage());
    }

    @Test
    void getCopyById_whenCopyExists_returnsCopy() {
        // Arrange
        String validId = "123e4567-e89b-12d3-a456-426614174000";
        Copy expectedCopy = new Copy();
        expectedCopy.setId(validId);

        when(copyRepository.BFindById(validId)).thenReturn(expectedCopy);

        // Act
        Copy actualCopy = copyService.getCopyById(validId);

        // Assert
        assertEquals(expectedCopy, actualCopy);
    }

    //findAllCopies
    @Test
    void findAllCopies_whenNoCopiesExist_returnsEmptyList() {
        // Arrange
        when(copyRepository.BFindAll()).thenReturn(Arrays.asList());

        // Act
        List<?> result = copyService.findAllCopies();

        // Assert
        assertTrue(result.isEmpty());
        verify(copyRepository, times(1)).BFindAll();
    }

    @Test
    void findAllCopies_whenCopiesExist_returnsListOfCopies() {
        // Arrange
        Copy copy1 = new Copy();
        copy1.setId("1");
        Copy copy2 = new Copy();
        copy2.setId("2");
        List<Copy> copies = Arrays.asList(copy1, copy2);

        when(copyRepository.BFindAll()).thenReturn(copies);

        // Act
        List<?> result = copyService.findAllCopies();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(copy1));
        assertTrue(result.contains(copy2));
        verify(copyRepository, times(1)).BFindAll();
    }
    //updateCopy

    @Test
    void updateCopies_whenCopyNotFound_throwsNotFoundException() {
        // Arrange
        when(copyRepository.BFindById("1")).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            copyService.updateCopies(copyRequest);
        });

        assertEquals("Copy with ID: 1 was not found.", exception.getMessage());
    }

    @Test
    void updateCopies_whenInvalidDisponibility_throwsBadAvailabilityException() {
        // Arrange
        copyRequest.setDisponibility(null);  // Invalid value

        when(copyRepository.BFindById("1")).thenReturn(existingCopy);

        // Act & Assert
        BadAvailabilityException exception = assertThrows(BadAvailabilityException.class, () -> {
            copyService.updateCopies(copyRequest);
        });

        assertEquals("Invalid availability for Copy ID: 1", exception.getMessage());
    }

    @Test
    void updateCopies_whenValidRequest_updatesCopyAndReturnsTrue() {
        // Arrange
        copyRequest.setDisponibility(CopyDispo.BORROWED);
        copyRequest.setState("Good");
        when(copyRepository.BFindById("1")).thenReturn(existingCopy);

        // Act
        boolean result = copyService.updateCopies(copyRequest);

        // Assert
        assertTrue(result);
        assertEquals(CopyDispo.BORROWED, existingCopy.getDisponibility());
        assertEquals("Good", existingCopy.getState());
        verify(copyRepository, times(1)).BUpdate(existingCopy);
    }

//    @Test
//    void updateCopies_whenInvalidState_throwsBadStateException() {
//        // Arrange
//        copyRequest.setState("INVALID_STATE"); // Invalid state
//
//        when(copyRepository.BFindById("1")).thenReturn(existingCopy);
//
//        // Act & Assert
//        BadStateException exception = assertThrows(BadStateException.class, () -> {
//            copyService.updateCopies(copyRequest);
//        });
//
//        assertEquals("Invalid state for Copy ID: 1", exception.getMessage());
//    }


    @Test
    void updateCopies_whenIllegalArgumentException_throwsBadValuesException() {
        // Arrange
        copyRequest.setDisponibility(CopyDispo.AVAILABLE);  // Valid value

        when(copyRepository.BFindById("1")).thenReturn(existingCopy);
        doThrow(new IllegalArgumentException()).when(copyRepository).BUpdate(any(Copy.class));

        // Act & Assert
        BadValuesException exception = assertThrows(BadValuesException.class, () -> {
            copyService.updateCopies(copyRequest);
        });

        assertEquals("Invalid values for Copy with ID: 1", exception.getMessage());
    }

    //save copies

    @Test
    void findCopiesByBook_whenBookIsNull_throwsNotNullException() {
        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            copyService.findCopiesByBook(null);
        });

        assertEquals("Copy must not be null", exception.getMessage());
    }

    @Test
    void findCopiesByBook_whenCopiesNotFound_throwsNotFoundException() {
        // Arrange
        Book book = new Book();
        book.setBookId("1");

        when(copyRepository.findCopyByBook(book)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            copyService.findCopiesByBook(book);
        });

        assertEquals("Copy not found for book with ID: 1", exception.getMessage());
    }

    @Test
    void findCopiesByBook_whenCopiesFound_returnsCopies() {
        // Arrange
        Book book = new Book();
        book.setBookId("1");
        List<Copy> expectedCopies = List.of(new Copy(), new Copy());

        when(copyRepository.findCopyByBook(book)).thenReturn(expectedCopies);

        // Act
        List<Copy> actualCopies = copyService.findCopiesByBook(book);

        // Assert
        assertEquals(expectedCopies, actualCopies);
    }

    @Test
    void findCopyByBarcode_whenBarcodeIsNull_throwsNotNullException() {
        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            copyService.findCopyByBarcode(null);
        });

        assertEquals("Barcode must not be null", exception.getMessage());
    }

    @Test
    void findCopyByBarcode_whenCopyNotFound_throwsNotFoundException() {
        // Arrange
        String barcode = "12345";

        when(copyRepository.findCopyByBarCode(barcode)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            copyService.findCopyByBarcode(barcode);
        });

        assertEquals("Copy not found with barcode: 12345", exception.getMessage());
    }

    @Test
    void findCopyByBarcode_whenCopyFound_returnsCopy() {
        // Arrange
        String barcode = "12345";
        Copy expectedCopy = new Copy();

        when(copyRepository.findCopyByBarCode(barcode)).thenReturn(expectedCopy);

        // Act
        Copy actualCopy = copyService.findCopyByBarcode(barcode);

        // Assert
        assertEquals(expectedCopy, actualCopy);
    }

    //ultimo metodo

    @Test
    void saveCopies_whenFileHasInvalidFormat_throwsRuntimeException() {
        // Arrange
        byte[] content = "invalid content".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            copyService.saveCopies(file);
        });
        assertEquals(exception.getMessage(),"Error al leer el archivo Excel");
    }

    @Test
    void saveCopies_whenIOExceptionOccurs_throwsRuntimeException() throws Exception {

        MockMultipartFile file = mock(MockMultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException("Test IOException"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            copyService.saveCopies(file);
        });
        assertTrue(exception.getMessage().contains("Error al leer el archivo Excel"));
    }


    @Test
    void saveCopies_whenFileIsValid_returnsNoErrors() throws Exception {
        // Mocking el comportamiento de bookRepository para retornar un libro válido cuando se le pase un ISBN
        Book bookMock = new Book();
        bookMock.setIsbn("1234567890");
        when(bookRepository.findByIsbn("1234567890")).thenReturn(bookMock);

        // Mocking el comportamiento de codeGenerator para generar un código de barras
        when(codeGenerator.generateCode(anyString())).thenReturn("generatedBarcode");

        // Mocking el comportamiento de copyRepository para simular la llamada a BSave
        doNothing().when(copyRepository).BSave(any(Copy.class));

        // Llamada al método saveCopies, que internamente llamará a createCopyByIsbn
        List<ObjectNode> result = copyService.saveCopies(createMockFile());

        // Verificación: verificamos que la lista de resultados esté vacía si no hubo errores
        assertTrue(result.isEmpty(), "The result should be empty when there are no errors");

        // Verificación: Verificar que el método BSave en copyRepository fue llamado
        verify(copyRepository, times(2)).BSave(any(Copy.class)); // Dos veces, una para cada guardar

        // Verificación de las interacciones correctas con los mocks
        verify(bookRepository, times(1)).findByIsbn("1234567890");
        verify(codeGenerator, times(1)).generateCode(anyString());

        // Verificación adicional: asegurarse de que el ID de la copia no sea nulo
        assertNotNull(existingCopy.getId(), "The copy ID should not be null");
    }

    // Método de ayuda para crear un archivo Mock
    private MultipartFile createMockFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("isbn");
        headerRow.createCell(1).setCellValue("state");
        headerRow.createCell(2).setCellValue("ubication");

        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("1234567890");
        dataRow1.createCell(1).setCellValue("Good");
        dataRow1.createCell(2).setCellValue("shelf1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", inputStream);
    }

    @Test
    void saveCopies_whenFileFormatIsInvalid_throwsRuntimeException() throws Exception {
        // Creamos un archivo que no es un archivo Excel válido
        byte[] invalidFileData = "This is not a valid Excel file.".getBytes();
        MultipartFile invalidFile = new MockMultipartFile("file", "invalid.txt", "text/plain", invalidFileData);

        // Llamada al método saveCopies y validación de que lanza la excepción esperada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            copyService.saveCopies(invalidFile);
        });

        // Verificación: que el mensaje de la excepción sea el esperado
        assertEquals("Error al leer el archivo Excel", exception.getMessage(), "The exception message should match the expected");
    }

}
