package edu.eci.cvds.Books.implementations;

import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.BadValuesException;
import edu.eci.cvds.Books.Exception.NotFoundException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Repository.Model.BasicBook;
import edu.eci.cvds.Books.Service.Implementations.ImpSubcategoryService;
import edu.eci.cvds.Books.Exception.BadObjectException;
import edu.eci.cvds.Books.Exception.NotNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ImpSubcategoryServiceTest {

    @Mock
    private BRepository subcategoryRepository;  // Mock del repositorio de subcategorías

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ImpSubcategoryService subcategoryService;  // El servicio que estamos probando

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
        subcategoryService = new ImpSubcategoryService(subcategoryRepository, bookRepository);

    }

    @Test
    void createSubcategory_whenSubcategoryIsNull_throwsNotNullException() {
        // Arrange
        Subcategory subcategory = null;

        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            subcategoryService.createSubcategory(subcategory);
        });
        assertEquals("Subcategory with null is Null.", exception.getMessage());
    }

    @Test
    void createSubcategory_whenDescriptionIsNull_throwsBadObjectException() {
        // Arrange
        Subcategory subcategory = new Subcategory();
        subcategory.setDescription(null);

        // Act & Assert
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            subcategoryService.createSubcategory(subcategory);
        });
        assertEquals("Subcategory with null or empty description is incomplete data.", exception.getMessage());
    }
    @Test
    void createSubcategory_whenValid_subcategoryIsCreated() {
        // Arrange
        Subcategory subcategory = new Subcategory();
        subcategory.setDescription("Test Subcategory");

        // Simulamos que al llamar a BSave, se asigna un ID
        doAnswer(invocation -> {
            Subcategory savedSubcategory = invocation.getArgument(0);  // Obtenemos el argumento pasado al método BSave
            savedSubcategory.setSubcategoryId("12345");  // Asignamos el ID simulado
            return null;  // Método void, no se devuelve nada
        }).when(subcategoryRepository).BSave(any(Subcategory.class));

        // Act
        String subcategoryId = subcategoryService.createSubcategory(subcategory);

        // Assert
        assertEquals("12345", subcategoryId);  // Comprobamos que el ID asignado sea "12345"
        verify(subcategoryRepository, times(1)).BSave(subcategory);  // Verificamos que BSave se haya llamado una vez
    }
    //DELETE

    @Test
    void deleteSubcategory_whenIdIsNull_throwsNotNullException() {
        // Arrange
        String subcategoryId = null;

        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            subcategoryService.deleteSubcategory(subcategoryId);
        });

        assertEquals("Subcategory with null or empty is Null.", exception.getMessage());
    }

    @Test
    void deleteSubcategory_whenSubcategoryNotFound_throwsNotFoundException() {
        // Arrange
        String subcategoryId = "12345";
        when(subcategoryRepository.BFindById(subcategoryId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            subcategoryService.deleteSubcategory(subcategoryId);
        });

        assertEquals("Subcategory with ID 12345 was not found.", exception.getMessage());
    }

    @Test
    void deleteSubcategory_whenValid_deletesSubcategory() {
        // Arrange
        String subcategoryId = "12345";
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(subcategoryId);
        when(subcategoryRepository.BFindById(subcategoryId)).thenReturn(subcategory);
        doNothing().when(subcategoryRepository).BDelete(subcategoryId);

        // Act
        subcategoryService.deleteSubcategory(subcategoryId);

        // Assert
        verify(subcategoryRepository, times(1)).BDelete(subcategoryId);
    }

    //GETSUBACTEGORY

    @Test
    void getSubcategory_whenIdIsNull_throwsNotNullException() {
        // Arrange
        String subcategoryId = null;

        // Act & Assert
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            subcategoryService.getSubcategory(subcategoryId);
        });

        assertEquals("Subcategory ID with null is Null.", exception.getMessage());
    }

    @Test
    void getSubcategory_whenNotFound_throwsNotFoundException() {
        // Arrange
        String subcategoryId = "12345";
        when(subcategoryRepository.BFindById(subcategoryId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            subcategoryService.getSubcategory(subcategoryId);
        });

        assertEquals("Subcategory with ID 12345 was not found.", exception.getMessage());
    }

    @Test
    void getSubcategory_whenValid_returnsSubcategory() {
        // Arrange
        String subcategoryId = "12345";
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(subcategoryId);
        when(subcategoryRepository.BFindById(subcategoryId)).thenReturn(subcategory);

        // Act
        Subcategory result = subcategoryService.getSubcategory(subcategoryId);

        // Assert
        assertEquals(subcategoryId, result.getSubcategoryId());
    }

    //GETSubCategories
    @Test
    void getSubcategories_whenSubcategoriesExist_returnsListOfSubcategories() {
        // Arrange
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setSubcategoryId("1");
        subcategory1.setDescription("Subcategory 1");
        subcategory1.setActive(true);

        Subcategory subcategory2 = new Subcategory();
        subcategory2.setSubcategoryId("2");
        subcategory2.setDescription("Subcategory 2");
        subcategory2.setActive(true);

        List<Subcategory> subcategories = Arrays.asList(subcategory1, subcategory2);
        when((List<Subcategory>)subcategoryRepository.BFindAll()).thenReturn(subcategories);

        // Act
        List<?> result = subcategoryService.getSubcategories();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(subcategory1));
        assertTrue(result.contains(subcategory2));
        verify(subcategoryRepository, times(1)).BFindAll();
    }

    @Test
    void getSubcategories_whenNoSubcategoriesExist_returnsEmptyList() {
        // Arrange
        when(subcategoryRepository.BFindAll()).thenReturn(Collections.emptyList());

        // Act
        List<?> result = subcategoryService.getSubcategories();

        // Assert
        assertTrue(result.isEmpty());
        verify(subcategoryRepository, times(1)).BFindAll();
    }

    //UPDATE
    @Test
    void updateSubcategory_whenSubcategoryExists_updatesSubcategory() {
        // Arrange
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId("1");
        subcategory.setDescription("Updated Description");

        Subcategory oldSubcategory = new Subcategory();
        oldSubcategory.setSubcategoryId("1");
        oldSubcategory.setDescription("Old Description");

        when(subcategoryRepository.BFindById("1")).thenReturn(oldSubcategory);

        // Act
        subcategoryService.updateSubcategory(subcategory);

        // Assert
        verify(subcategoryRepository, times(1)).BFindById("1");
        verify(subcategoryRepository, times(1)).BUpdate(subcategory);

        // Ensure the description was updated correctly
        assertEquals("Updated Description", subcategory.getDescription());

        // Verify no other interactions with the repository
        verifyNoMoreInteractions(subcategoryRepository);
    }

    @Test
    void updateSubcategory_whenSubcategoryDoesNotExist_throwsNotFoundException() {
        // Arrange
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId("1");
        subcategory.setDescription("Updated Description");

        when(subcategoryRepository.BFindById("1")).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            subcategoryService.updateSubcategory(subcategory);
        });

        assertEquals("Subcategory with ID 1 was not found.", exception.getMessage());
        verify(subcategoryRepository, times(0)).BUpdate(any(Subcategory.class));
    }

    @Test
    void updateSubcategory_whenDescriptionIsNullOrEmpty_throwsBadObjectException() {
        // Arrange
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId("1");
        subcategory.setDescription(""); // Invalid description

        Subcategory oldSubcategory = new Subcategory();
        oldSubcategory.setSubcategoryId("1");
        oldSubcategory.setDescription("Old Description");

        when(subcategoryRepository.BFindById("1")).thenReturn(oldSubcategory);

        // Act & Assert
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            subcategoryService.updateSubcategory(subcategory);
        });

        assertEquals("Subcategory with null or empty description is incomplete data.", exception.getMessage());
        verify(subcategoryRepository, times(0)).BUpdate(any(Subcategory.class));
    }

    @Test
    void updateSubcategory_whenInvalidValuesProvided_throwsBadValuesException() {
        // Arrange
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId("1");
        subcategory.setDescription("Updated Description");

        Subcategory oldSubcategory = new Subcategory();
        oldSubcategory.setSubcategoryId("1");
        oldSubcategory.setDescription("Old Description");

        when(subcategoryRepository.BFindById("1")).thenReturn(oldSubcategory);
        doThrow(IllegalArgumentException.class).when(subcategoryRepository).BUpdate(subcategory);

        // Act & Assert
        BadValuesException exception = assertThrows(BadValuesException.class, () -> {
            subcategoryService.updateSubcategory(subcategory);
        });

        assertEquals("Invalid values for Subcategory with ID: 1", exception.getMessage());
        verify(subcategoryRepository, times(1)).BUpdate(subcategory);
    }
    //get books
    @Test
    void getBooks_whenSubcategoryExistsAndHasBooks_returnsListOfBooks() {
        // Arrange
        String idSubcategory = "1";
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(idSubcategory);

        BasicBook book1 = mock(BasicBook.class);
        BasicBook book2 = mock(BasicBook.class);
        List<BasicBook> books = Arrays.asList(book1, book2);

        when(subcategoryRepository.BFindById(idSubcategory)).thenReturn(subcategory);
        when(bookRepository.findBySubcategories(subcategory)).thenReturn(books);

        // Act
        List<?> result = subcategoryService.getBooks(idSubcategory);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));
        verify(subcategoryRepository, times(1)).BFindById(idSubcategory);
        verify(bookRepository, times(1)).findBySubcategories(subcategory);
    }

    @Test
    void getBooks_whenSubcategoryDoesNotExist_throwsNotFoundException() {
        // Arrange
        String idSubcategory = "1";

        when(subcategoryRepository.BFindById(idSubcategory)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> subcategoryService.getBooks(idSubcategory));
        verify(subcategoryRepository, times(1)).BFindById(idSubcategory);
        verify(bookRepository, times(0)).findBySubcategories(any(Subcategory.class));
    }

    @Test
    void getBooks_whenSubcategoryExistsButHasNoBooks_throwsNotFoundException() {
        // Arrange
        String idSubcategory = "1";
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(idSubcategory);

        when(subcategoryRepository.BFindById(idSubcategory)).thenReturn(subcategory);
        when(bookRepository.findBySubcategories(subcategory)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> subcategoryService.getBooks(idSubcategory));
        verify(subcategoryRepository, times(1)).BFindById(idSubcategory);
        verify(bookRepository, times(1)).findBySubcategories(subcategory);
    }

}

