package edu.eci.cvds.Books.Service.Implementations;

import edu.eci.cvds.Books.Codes.GenerateCodeException;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.BookException;
import edu.eci.cvds.Books.Exception.CopyException;
import edu.eci.cvds.Books.Exception.SubcategoryException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Service.SubcategoryService;
import org.hibernate.TransientObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ImpSub")
public class ImpSubcategoryService implements SubcategoryService {
    private final BRepository subcategoryRepository;
    private final BRepository categoryRepository;
    @Autowired
    public ImpSubcategoryService(@Qualifier("SubRepo") BRepository subcategoryRepository, @Qualifier("CatRepo") BRepository categoryRepository){
        this.subcategoryRepository=subcategoryRepository;
        this.categoryRepository=categoryRepository;
    }
    @Override
    public void createSubcategory(String categoryId,Subcategory subcategory) {

        if (subcategory == null){
            throw new SubcategoryException(SubcategoryException.notNull);
        } if(subcategory.getDescription() == null || subcategory.getDescription().isEmpty() ){
            throw new SubcategoryException(SubcategoryException.badSubcategory);
        }
        Category category = (Category) categoryRepository.BFindById(categoryId);
        if (category == null){
            throw new SubcategoryException(SubcategoryException.badCategory);
        }
        subcategory.setCategory(category);
        subcategoryRepository.BSave(subcategory);
    }

    @Override
    public void deleteSubcategory(String subcategoryId) {
        if (subcategoryId == null || subcategoryId.isEmpty() ){
            throw new SubcategoryException(SubcategoryException.notNull);
        } if (subcategoryRepository.BFindById(subcategoryId) == null){
            throw new SubcategoryException(SubcategoryException.notFound);
        }
        subcategoryRepository.BDelete(subcategoryId);
    }

    @Override
    public Subcategory getSubcategory(String subcategoryId) {
        if (subcategoryId == null){
            throw new SubcategoryException(SubcategoryException.dataNotNull);
        }
        Subcategory subcategory = (Subcategory) subcategoryRepository.BFindById(subcategoryId);
        if (subcategory == null){
            throw new SubcategoryException(SubcategoryException.notFound);
        }
        return subcategory;
    }

    @Override
    public List<?> getSubcategories() {
        return this.subcategoryRepository.BFindAll();
    }

    @Override
    public void updateSubcategory(Subcategory subcategory) {
        try {
            Subcategory oldSubcategory = (Subcategory) subcategoryRepository.BFindById(subcategory.getSubcategoryId());
            if (oldSubcategory == null) {
                throw new SubcategoryException(SubcategoryException.notFound);
            } else {
                if ( subcategory.getDescription() == null || subcategory.getDescription().isEmpty()) {
                    throw new SubcategoryException(SubcategoryException.badSubcategory);
                }
            }
            (subcategoryRepository).BUpdate(subcategory);
        } catch (IllegalArgumentException ex){
            throw new SubcategoryException(SubcategoryException.badValues);
        }
    }
}
