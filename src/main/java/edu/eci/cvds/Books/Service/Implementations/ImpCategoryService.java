package edu.eci.cvds.Books.Service.Implementations;


import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Repository.Model.BasicBook;
import edu.eci.cvds.Books.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service("ImpCat")
public class ImpCategoryService implements CategoryService {
    private final BRepository categoryRepository;
    private final BRepository bookRepository;
    @Autowired
    public ImpCategoryService(@Qualifier("CatRepo") BRepository categoryRepository,@Qualifier("BookRepo") BRepository bookRepository){
        this.categoryRepository=categoryRepository;
        this.bookRepository=bookRepository;
    }
    @Override
    public String createCategory(Category category) {
        if (category == null){
            throw new NotNullException("Category", "null");
        }
        if(category.getDescription() == null){
            throw new BadObjectException("Category", "null description");
        }
        this.categoryRepository.BSave(category);
        return category.getCategoryId();
    }

    @Override
    public void deleteCategory(String categoryId) {
        if (categoryId == null || categoryId == "" ){
            throw new NotNullException("Category ID", "null");
        } if (categoryRepository.BFindById(categoryId) == null){
            throw new NotFoundException("Category", categoryId);
        }
        categoryRepository.BDelete(categoryId);
    }

    @Override
    public Category getCategory(String categoryId) {
        if (categoryId == null){
            throw new NotNullException("Catgory ID", "null");
        }
        Category category = (Category)categoryRepository.BFindById(categoryId);
        if (category == null){
            throw new NotFoundException("Category", categoryId);
        }
        return category;
    }

    @Override
    public List<?> getCategories() {
        return this.categoryRepository.BFindAll();
    }

    @Override
    public void updateCategory(Category category) {
        try {
            Category oldCategory = (Category)categoryRepository.BFindById(category.getCategoryId());
            if (oldCategory == null) {
                throw new NotFoundException("Category", category.getCategoryId());
            } else {
                if ( category.getDescription() == null || category.getDescription().isEmpty()) {
                    throw new BadObjectException("Category", category.getDescription());
                }
            }
            (categoryRepository).BUpdate(category);
        } catch (IllegalArgumentException ex){
            throw new BadValuesException("Invalid values for Category with ID", category.getCategoryId());
        }
    }
    @Override
    public List<?> getBooks(String idCategory){
        Category category = (Category) this.categoryRepository.BFindById(idCategory);
        if(category==null) throw new NotFoundException(idCategory);
        List<BasicBook> books = ((BookRepository)this.bookRepository).findByCategories(category);
        if(books.isEmpty()) throw new NotFoundException("Books");
        return books;
    }
    @Override
    public HashMap<String,List<Book>> getBooksByCategories(){
        HashMap<String,List<Book>> booksByCategory = new HashMap<>();
        List<Category> categories = (List<Category>) this.categoryRepository.BFindAll();
        if(categories.isEmpty()) throw new NotFoundException("Books");
        for(Category category: categories) {
            List<Book> books = ((BookRepository) this.bookRepository).findByCategories(category);
            booksByCategory.put(category.getDescription(), books);
        }
        return booksByCategory;
    }
}
