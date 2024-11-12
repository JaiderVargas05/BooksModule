package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("CatRepo")
public interface CategoryRepository extends BRepository, JpaRepository<Category,String> {
    @Override
    public default void BSave(Object category){
        save((Category) category);
    }

    @Override
    public default Object BFindById(String id){
        return findById(id).orElse(null);
    }

    @Override
    public default void BDelete(String id){
        Category category = findById(id).orElse(null);
        category.setActive(false);
        this.BSave(category);
    }

    @Override
    public default List<?> BFindAll(){
        return findAll();
    }

    @Override
    public default void BUpdate(Object category){
        save((Category) category);
    }
    @Override
    public default List<Object> BFindAllById(List<String> Ids){
        return BFindAllById(Ids);
    }
}
