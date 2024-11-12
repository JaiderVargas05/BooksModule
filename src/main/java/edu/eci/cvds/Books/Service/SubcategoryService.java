package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;

import java.util.List;

public interface SubcategoryService {
    public void createSubcategory(String idCategory,Subcategory subcategory);
    public void deleteSubcategory(String idSubcategory);
    public Subcategory getSubcategory(String idSubcategory);
    public List<?> getSubcategories();
    public void updateSubcategory(Subcategory subcategory);
}
