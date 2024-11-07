package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Codes.CodeGenerator;
import edu.eci.cvds.Books.Codes.GenerateCodeException;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.CopyDispo;
import edu.eci.cvds.Books.Domain.CopyState;
import edu.eci.cvds.Books.Exception.CopyException;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.CopyRepository;
import org.hibernate.TransientObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("CopyImp")
public class ImpCopyService implements CopyService {

    private final BRepository copyRepository;
    private final BRepository bookRepository;
    private final CodeGenerator codeGenerator;
    @Autowired
    public ImpCopyService(@Qualifier("CopyRep") BRepository copyRepository,@Qualifier("BookRepo") BRepository bookRepository,CodeGenerator codeGenerator) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
        this.codeGenerator=codeGenerator;
    }
    @Override
    public boolean createCopy(String bookId, Copy e) throws CopyException {
        try{
            if (e == null){
                throw new CopyException(CopyException.notNull);
            } if(e.getState() == null){
                throw new CopyException(CopyException.badEjemplar);
            }
            Book book = (Book) bookRepository.BFindById(bookId);
            if (book == null){
                throw new CopyException(CopyException.badBook);
            }
            e.setBook(book);
            // guardar ejemplar para que se genere el ID
            copyRepository.BSave(e);
            // genera código de barras
            String barcode = codeGenerator.generateCode(e.getId());
            e.setBarCode(barcode);
            // actualiza ejemplar con el código de barras
            copyRepository.BSave(e);

            return true;
        } catch (IllegalArgumentException ex){
            throw new CopyException(CopyException.badState);
        } catch (TransientObjectException | GenerateCodeException ex){
            throw new CopyException(CopyException.badBook);
        }
    }

    public boolean deleteCopy(Copy e) throws CopyException {
        if (e == null){
            throw new CopyException(CopyException.notNull);
        } if (copyRepository.BFindById(e.getId()) == null){
            throw new CopyException(CopyException.notFound);
        }
        copyRepository.BDelete(e.getId());
        return true;
    }

    public Copy getCopyById(String id) throws CopyException {
        if (id == null){
            throw new CopyException(CopyException.dataNotNull);
        }
        if(!isValidIdFormat(id)){
            throw new CopyException(CopyException.badFormat);
        }
        Copy copy = (Copy)copyRepository.BFindById(id);
        if (copy == null){
            throw new CopyException(CopyException.notFound);
        }
        return copy;
    }
    private boolean isValidIdFormat(String id) {
        return id.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    }
    public List<?> findAllCopies(){
        return copyRepository.BFindAll();
    }

    public boolean updateCopies(Copy e) throws CopyException {
        try {
            Copy oldCopy = (Copy)copyRepository.BFindById(e.getId());
            if (oldCopy == null) {
                throw new CopyException(CopyException.notFound);
            } else {
                if (e.getBook() == null || e.getState() == null || e.getDisponibility() == null || e.getBarCode() == null) {
                    throw new CopyException(CopyException.badEjemplar);
                }
                if (e.getState() != CopyState.DAMAGED || e.getState() != CopyState.GOOD_CONDITION || e.getState() != CopyState.FAIR) {
                    throw new CopyException(CopyException.badState);
                }
                if (e.getDisponibility() != CopyDispo.AVAILABLE || e.getDisponibility() != CopyDispo.BORROWED) {
                    throw new CopyException(CopyException.badDispo);
                }
            }
             copyRepository.BUpdate(e);
            return true;
        } catch (IllegalArgumentException ex){
            throw new CopyException(CopyException.badValues);
        }
    }

    public List<Copy> findCopiesByBook(Book book) throws CopyException {
        if(book == null){
            throw new CopyException(CopyException.badBook);
        }
        List<Copy> copies = ((CopyRepository) copyRepository).findCopyByBook(book);
        if(copies == null || copies.isEmpty()){
            throw new CopyException(CopyException.noEjemplarsForBook);
        }
        return copies;
    }
    public Copy findCopyByBarcode(String barcode) throws CopyException {
        if(barcode == null){
            throw new CopyException(CopyException.barCodeIncorrect);
        }
        Copy copy = ((CopyRepository) copyRepository).findCopyByBarCode(barcode);
        if (copy == null){
            throw new CopyException(CopyException.notFound);
        }
        return copy;
    }

}
