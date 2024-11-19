package edu.eci.cvds.Books.Service.Implementations;


import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Service.SubcategoryService;
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
    public String createSubcategory(String categoryId,Subcategory subcategory) {

        if (subcategory == null){
            throw new NotNullException("Subcategory", "null");
        } if(subcategory.getDescription() == null || subcategory.getDescription().isEmpty() ){
            throw new BadObjectException("Subcategory", "null or empty description");
        }
        Category category = (Category) categoryRepository.BFindById(categoryId);
        if (category == null){
            throw new NotFoundException("Category", categoryId);
        }
        subcategory.setCategory(category);
        subcategoryRepository.BSave(subcategory);
        return subcategory.getSubcategoryId();
    }

    @Override
    public void deleteSubcategory(String subcategoryId) {
        if (subcategoryId == null || subcategoryId.isEmpty() ){
            throw new NotNullException("Subcategory","null or empty");
        } if (subcategoryRepository.BFindById(subcategoryId) == null){
            throw new NotFoundException("Subcategory", subcategoryId);
        }
        subcategoryRepository.BDelete(subcategoryId);
    }

    @Override
    public Subcategory getSubcategory(String subcategoryId) {
        if (subcategoryId == null){
            throw new NotNullException("Subcategory ID", "null");
        }
        Subcategory subcategory = (Subcategory) subcategoryRepository.BFindById(subcategoryId);
        if (subcategory == null){
            throw new NotFoundException("Subcategory", subcategoryId);
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
                throw new NotFoundException("Subcategory", subcategory.getSubcategoryId());
            } else {
                if ( subcategory.getDescription() == null || subcategory.getDescription().isEmpty()) {
                    throw new BadObjectException("Subcategory", subcategory.getDescription());
                }
            }
            (subcategoryRepository).BUpdate(subcategory);
        } catch (IllegalArgumentException ex){
            throw new BadValuesException("Invalid values for Subcategory with ID",subcategory.getSubcategoryId());
        }
    }
}
