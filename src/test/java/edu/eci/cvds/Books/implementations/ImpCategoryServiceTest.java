package edu.eci.cvds.Books.implementations;

import edu.eci.cvds.Books.Controller.RequestModel.CategoryRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Repository.CategoryRepository;
import edu.eci.cvds.Books.Repository.Model.BasicBook;
import edu.eci.cvds.Books.Service.Implementations.ImpCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImpCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ImpCategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new ImpCategoryService(categoryRepository, bookRepository);
    }
    @Test
    void createCategory_whenCategoryIsNull_throwsNotNullException() {
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            categoryService.createCategory(null);
        });

        assertEquals("Category must not be null", exception.getMessage());
    }

    @Test
    void createCategory_whenDescriptionIsNull_throwsBadObjectException() {
        Category category = new Category();
        category.setDescription(null);
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            categoryService.createCategory(category);
        });
        assertEquals("Category with null description is incomplete data.", exception.getMessage());
    }

    @Test
    void createCategory_whenDescriptionExists_throwsDuplicateObjectException() {
        Category category = new Category();
        category.setDescription("Test Description");
        when(categoryRepository.findByDescription(anyString())).thenReturn(new Category());
        DuplicateObjectException exception = assertThrows(DuplicateObjectException.class, () -> {
            categoryService.createCategory(category);
        });
        assertEquals("Category Test Description already exists.", exception.getMessage());
    }

    @Test
    void createCategory_whenValid_savesCategoryAndReturnsId() {
        Category category = new Category();
        category.setCategoryId(UUID.randomUUID().toString());
        category.setDescription("Test Description");
        when(categoryRepository.findByDescription(anyString())).thenReturn(null);
        doNothing().when(categoryRepository).BSave(any(Category.class));
        String categoryId = categoryService.createCategory(category);
        assertEquals(category.getCategoryId(), categoryId);
        verify(categoryRepository, times(1)).BSave(category);
    }

    //DELETE
    @Test
    void deleteCategory_whenCategoryIdIsNull_throwsNotNullException() {
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            categoryService.deleteCategory(null);
        });
        assertEquals("Category ID must not be null", exception.getMessage());
    }

    @Test
    void deleteCategory_whenCategoryIdIsEmpty_throwsNotNullException() {
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            categoryService.deleteCategory("");
        });
        assertEquals("Category ID must not be null", exception.getMessage());
    }

    @Test
    void deleteCategory_whenCategoryNotFound_throwsNotFoundException() {
        String categoryId = "nonexistent-id";
        when(categoryRepository.BFindById(categoryId)).thenReturn(null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });
        assertEquals("Category with ID: nonexistent-id was not found.", exception.getMessage());
    }

    @Test
    void deleteCategory_whenValid_deletesCategory() {
        String categoryId = "valid-id";
        Category category = new Category();
        category.setCategoryId(categoryId);
        when(categoryRepository.BFindById(categoryId)).thenReturn(category);
        doNothing().when(categoryRepository).BDelete(categoryId);
        categoryService.deleteCategory(categoryId);
        verify(categoryRepository, times(1)).BDelete(categoryId);
    }

    //GET CATOGORY


    @Test
    void getCategory_whenCategoryIdIsNull_throwsNotNullException() {
        NotNullException exception = assertThrows(NotNullException.class, () -> {
            categoryService.getCategory(null);
        });
        assertEquals("Category ID must not be null", exception.getMessage());
    }

    @Test
    void getCategory_whenCategoryNotFound_throwsNotFoundException() {
        String categoryId = "nonexistent-id";
        when(categoryRepository.BFindById(anyString())).thenReturn(null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categoryService.getCategory(categoryId);
        });
        assertEquals("Category with ID: nonexistent-id was not found.", exception.getMessage());
    }

    @Test
    void getCategory_whenValid_returnsCategory() {
        String categoryId = "valid-id";
        Category expectedCategory = new Category();
        expectedCategory.setCategoryId(categoryId);
        when(categoryRepository.BFindById(anyString())).thenReturn(expectedCategory);
        Category actualCategory = categoryService.getCategory(categoryId);
        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepository, times(1)).BFindById(categoryId);
    }

    @Test
    void getCategories_returnsListOfCategories() {
        List<Category> expectedCategories = Arrays.asList(
                new Category(UUID.randomUUID().toString(), "Fiction", true),
                new Category(UUID.randomUUID().toString(), "Non-Fiction", true)
        );
        doAnswer(invocation -> expectedCategories).when(categoryRepository).BFindAll();
        List<?> actualCategories = categoryService.getCategories();
        assertEquals(expectedCategories, actualCategories);
        verify(categoryRepository, times(1)).BFindAll();
    }

    //update
    @Test
    void updateCategory_whenCategoryExists_updatesCategory() {
        Category existingCategory = new Category(UUID.randomUUID().toString(), "Existing Description", true);
        Category updatedCategory = new Category(existingCategory.getCategoryId(), "Updated Description", true);
        when(categoryRepository.BFindById(existingCategory.getCategoryId())).thenReturn(existingCategory);
        doNothing().when(categoryRepository).BUpdate(any(Category.class));
        categoryService.updateCategory(updatedCategory);
        verify(categoryRepository, times(1)).BUpdate(updatedCategory);
    }

    @Test
    void updateCategory_whenCategoryNotFound_throwsNotFoundException() {
        Category category = new Category(UUID.randomUUID().toString(), "Description", true);
        when(categoryRepository.BFindById(category.getCategoryId())).thenReturn(null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categoryService.updateCategory(category);
        });
        assertEquals("Category with ID: " + category.getCategoryId() + " was not found.", exception.getMessage());
    }

    @Test
    void updateCategory_whenDescriptionIsNull_throwsBadObjectException() {
        Category existingCategory = new Category(UUID.randomUUID().toString(), "Existing Description", true);
        Category updatedCategory = new Category(existingCategory.getCategoryId(), null, true);
        when(categoryRepository.BFindById(existingCategory.getCategoryId())).thenReturn(existingCategory);
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            categoryService.updateCategory(updatedCategory);
        });
        assertEquals("Category with null is incomplete data.", exception.getMessage());
    }

    @Test
    void updateCategory_whenDescriptionIsEmpty_throwsBadObjectException() {
        Category existingCategory = new Category(UUID.randomUUID().toString(), "Existing Description", true);
        Category updatedCategory = new Category(existingCategory.getCategoryId(), "", true);
        when(categoryRepository.BFindById(existingCategory.getCategoryId())).thenReturn(existingCategory);
        BadObjectException exception = assertThrows(BadObjectException.class, () -> {
            categoryService.updateCategory(updatedCategory);
        });
        assertEquals("Category with  is incomplete data.", exception.getMessage());
    }

    @Test
    void updateCategory_whenIllegalArgumentExceptionThrown_throwsBadValuesException() {
        Category existingCategory = new Category(UUID.randomUUID().toString(), "Existing Description", true);
        Category updatedCategory = new Category(existingCategory.getCategoryId(), "Updated Description", true);
        when(categoryRepository.BFindById(existingCategory.getCategoryId())).thenReturn(existingCategory);
        doThrow(new IllegalArgumentException()).when(categoryRepository).BUpdate(any(Category.class));
        BadValuesException exception = assertThrows(BadValuesException.class, () -> {
            categoryService.updateCategory(updatedCategory);
        });
        assertEquals("Invalid values for Category with ID: " + updatedCategory.getCategoryId(), exception.getMessage());
    }

 //get books

    @Test
    void getBooks_whenCategoryNotFound_throwsNotFoundException() {
        String categoryId = UUID.randomUUID().toString();
        when(categoryRepository.BFindById(categoryId)).thenReturn(null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categoryService.getBooks(categoryId);
        });
        assertEquals("Category with ID: " + categoryId + " was not found.", exception.getMessage());
        verify(categoryRepository, times(1)).BFindById(categoryId);
        verify(bookRepository, never()).findByCategories(any(Category.class));
    }

    @Test
    void getBooks_whenNoBooksFound_throwsNotFoundException() {
        Category category = new Category(UUID.randomUUID().toString(), "Test Category", true);
        List<BasicBook> emptyBookList = new ArrayList<>();
        when(categoryRepository.BFindById(category.getCategoryId())).thenReturn(category);
        when(bookRepository.findByCategories(category)).thenReturn(emptyBookList);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categoryService.getBooks(category.getCategoryId());
        });
        assertEquals("Books not found.", exception.getMessage());
        verify(categoryRepository, times(1)).BFindById(category.getCategoryId());
        verify(bookRepository, times(1)).findByCategories(category);
    }

    @Test
    void testCategoryRequest() {
        CategoryRequest categoryRequest = new CategoryRequest();
        String categoryId = "cat123";
        String description = "A category for testing purposes";
        boolean active = true;

        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        Subcategory subcategory1 = new Subcategory();
        Subcategory subcategory2 = new Subcategory();
        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(subcategory1);
        subcategories.add(subcategory2);

        categoryRequest.setCategoryId(categoryId);
        categoryRequest.setDescription(description);
        categoryRequest.setBooks(books);
        categoryRequest.setSubcategories(subcategories);
        categoryRequest.setActive(active);

        assertEquals(categoryId, categoryRequest.getCategoryId());
        assertEquals(description, categoryRequest.getDescription());
        assertEquals(books, categoryRequest.getBooks());
        assertEquals(subcategories, categoryRequest.getSubcategories());
        assertTrue(categoryRequest.isActive());
    }



}
