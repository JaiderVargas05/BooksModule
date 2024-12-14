package edu.eci.cvds.Books.Repository;

import edu.eci.cvds.Books.Domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("SubRepo")
public interface SubcategoryRepository extends BRepository,JpaRepository<Subcategory,String>{
    @Override
    public default void BSave(Object subcategory){
        save((Subcategory) subcategory);
    }

    @Override
    public default Object BFindById(String id){
        return findById(id).orElse(null);
    }

    @Override
    public default void BDelete(String id){
        Subcategory subcategory = findById(id).orElse(null);
        subcategory.setActive(false);
        this.BSave(subcategory);
    }

    @Override
    @Query("SELECT s FROM subcategory s  WHERE s.active=true")
    public default List<?> BFindAll(){
        return findAll();
    }

    @Override
    public default void BUpdate(Object subcategory){
        save((Subcategory) subcategory);
    }

    @Override
    public default List<?> BFindAllById(List<String> Ids){
        return findAllById(Ids);
    }
}
