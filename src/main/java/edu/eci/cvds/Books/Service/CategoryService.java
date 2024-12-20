package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Repository.Model.BasicBook;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    public String createCategory(Category category);
    public void deleteCategory(String idCategory);
    public Category getCategory(String idCategory);
    public List<?> getCategories();
    public void updateCategory(Category category);
    public List<?> getBooks(String idCategory);




}
