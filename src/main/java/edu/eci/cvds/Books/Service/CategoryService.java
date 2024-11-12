package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;

import java.util.List;

public interface CategoryService {
    public void createCategory(Category category);
    public void deleteCategory(String idCategory);
    public Category getCategory(String idCategory);
    public List<?> getCategories();
    public List<Subcategory> getSubcategories(String idCategory);
    public void updateCategory(Category category);
}
