package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;

import java.util.List;

public interface SubcategoryService {
    public String createSubcategory(Subcategory subcategory);
    public void deleteSubcategory(String idSubcategory);
    public Subcategory getSubcategory(String idSubcategory);
    public List<?> getSubcategories();
    public void updateSubcategory(Subcategory subcategory);

    public List<Book> getBooks(String idSubcategory);
}
