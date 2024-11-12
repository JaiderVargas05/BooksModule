package edu.eci.cvds.Books.Service.Implementations;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.BookException;
import edu.eci.cvds.Books.Exception.CategoryException;
import edu.eci.cvds.Books.Exception.CopyException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ImpCat")
public class ImpCategoryService implements CategoryService {
    private final BRepository categoryRepository;
    @Autowired
    public ImpCategoryService(@Qualifier("CatRepo") BRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    @Override
    public void createCategory(Category category) {
        if (category == null){
            throw new CategoryException(CategoryException.notNull);
        }
        if(category.getDescription() == null){
            throw new CategoryException(CategoryException.badCategory);
        }
        this.categoryRepository.BSave(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        if (categoryId == null || categoryId == "" ){
            throw new CategoryException(CategoryException.notNull);
        } if (categoryRepository.BFindById(categoryId) == null){
            throw new CategoryException(CategoryException.notFound);
        }
        categoryRepository.BDelete(categoryId);
    }

    @Override
    public Category getCategory(String categoryId) {
        if (categoryId == null){
            throw new CategoryException(CategoryException.dataNotNull);
        }
        Category category = (Category)categoryRepository.BFindById(categoryId);
        if (category == null){
            throw new CategoryException(CategoryException.notFound);
        }
        return category;
    }

    @Override
    public List<?> getCategories() {
        return this.categoryRepository.BFindAll();
    }

    @Override
    public List<Subcategory> getSubcategories(String categoryId) {
        Category category = this.getCategory(categoryId);
        return category.getSubcategories();
    }

    @Override
    public void updateCategory(Category category) {
        try {
            Category oldCategory = (Category)categoryRepository.BFindById(category.getCategoryId());
            if (oldCategory == null) {
                throw new CategoryException(CategoryException.notFound);
            } else {
                if ( category.getDescription() == null || category.getDescription().isEmpty()) {
                    throw new CategoryException(CategoryException.badCategory);
                }
            }
            (categoryRepository).BUpdate(category);
        } catch (IllegalArgumentException ex){
            throw new CategoryException(CategoryException.badValues);
        }
    }
}
