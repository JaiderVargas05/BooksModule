package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Ejemplar;
import edu.eci.cvds.Books.Domain.EjemplarDispo;
import edu.eci.cvds.Books.Domain.EjemplarState;
import edu.eci.cvds.Books.Exception.EjemplarException;
import edu.eci.cvds.Books.Repository.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("ejemImp")
public class ImpEjemplarService implements EjemplarService {
    EjemplarRepository ejemplarRepository;
    @Autowired
    public ImpEjemplarService(@Qualifier("EjRepSql") EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    public boolean createEjemplar(Ejemplar e) throws EjemplarException{
        try{
            if (e == null){
                throw new EjemplarException(EjemplarException.notNull);
            } if(e.getBook() == null || e.getState() == null){
                throw new EjemplarException(EjemplarException.badEjemplar);
            }
            ejemplarRepository.createEjemplar(e);
            return true;
        } catch (IllegalArgumentException ex){
            throw new EjemplarException(EjemplarException.badState);
        }
    }

    public boolean deleteEjemplar(Ejemplar e) throws EjemplarException{
        if (e == null){
            throw new EjemplarException(EjemplarException.notNull);
        } if (ejemplarRepository.findEjemplarById(e.getId()) == null){
            throw new EjemplarException(EjemplarException.notFound);
        }
        ejemplarRepository.deleteEjemplar(e);
        return true;
    }

    public Ejemplar getEjemplarById(String id) throws EjemplarException{
        if (id == null){
            throw new EjemplarException(EjemplarException.dataNotNull);
        }
        if(!isValidIdFormat(id)){
            throw new EjemplarException(EjemplarException.badFormat);
        }
        Ejemplar ejemplar = ejemplarRepository.findEjemplarById(id);
        if (ejemplar == null){
            throw new EjemplarException(EjemplarException.notFound);
        }
        return ejemplar;
    }
    private boolean isValidIdFormat(String id) {
        return id.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    }
    public List<Ejemplar> findAllEjemplars(){
        return ejemplarRepository.findAllEjemplars();
    }

    public boolean updateEjemplar(Ejemplar e) throws EjemplarException{
        try {
            Ejemplar oldEjemplar = ejemplarRepository.findEjemplarById(e.getId());
            if (oldEjemplar == null) {
                throw new EjemplarException(EjemplarException.notFound);
            } else {
                if (e.getBook() == null || e.getState() == null || e.getDisponibility() == null || e.getBarCode() == null) {
                    throw new EjemplarException(EjemplarException.badEjemplar);
                }
                if (e.getState() != EjemplarState.DAMAGED || e.getState() != EjemplarState.GOOD_CONDITION || e.getState() != EjemplarState.FAIR) {
                    throw new EjemplarException(EjemplarException.badState);
                }
                if (e.getDisponibility() != EjemplarDispo.AVAILABLE || e.getDisponibility() != EjemplarDispo.BORROWED) {
                    throw new EjemplarException(EjemplarException.badDispo);
                }
            }
            ejemplarRepository.updateEjemplar(e);
            return true;
        } catch (IllegalArgumentException ex){
            throw new EjemplarException(EjemplarException.badValues);
        }
    }

    public List<Ejemplar> findEjemplarsByBook(Book book) throws EjemplarException{
        if(book == null){
            throw new EjemplarException(EjemplarException.badBook);
        }
        List<Ejemplar> ejemplars = ejemplarRepository.findEjemplarByBook(book);
        if(ejemplars == null || ejemplars.size() == 0){
            throw new EjemplarException(EjemplarException.noEjemplarsForBook);
        }
        return ejemplars;
    }
    public Ejemplar findEjemplarByBarcode(Integer barcode) throws EjemplarException{
        if(barcode == null){
            throw new EjemplarException(EjemplarException.barCodeIncorrect);
        }
        Ejemplar ejemplar = ejemplarRepository.findEjemplarByBarCode(barcode);
        if (ejemplar == null){
            throw new EjemplarException(EjemplarException.notFound);
        }
        return ejemplar;
    }

}
