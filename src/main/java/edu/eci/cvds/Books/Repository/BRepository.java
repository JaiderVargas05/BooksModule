package edu.eci.cvds.Books.Repository;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BRepository {
    void BSave(Object object);
    Object BFindById(String id);
    void BDelete(Object object);
    List<?> BFindAll();
}
