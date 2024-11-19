package edu.eci.cvds.Books.Service.Implementations;

import edu.eci.cvds.Books.Codes.CodeGenerator;
import edu.eci.cvds.Books.Codes.GenerateCodeException;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.CopyDispo;
import edu.eci.cvds.Books.Domain.CopyState;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.CopyRepository;
import edu.eci.cvds.Books.Service.CopyService;
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
    public String createCopy(String bookId, Copy e)  {
        try{
            if (e == null){
                throw new NotNullException("Copy", "null");
            } if(e.getState() == null){
                throw new BadStateException("Copy", e.getId());
            }
            Book book = (Book) bookRepository.BFindById(bookId);
            if (book == null){
                throw new NotFoundException("Copy", bookId);
            }
            e.setBook(book);
            copyRepository.BSave(e);
            String barcode = codeGenerator.generateCode(e.getId());
            e.setBarCode(barcode);
            copyRepository.BSave(e);

            return e.getId();
        } catch (IllegalArgumentException ex){
            throw new BadStateException("Copy", e.getId());
        } catch (TransientObjectException | GenerateCodeException ex){
            throw new BadObjectException("Copy", bookId);
        }
    }

    public boolean deleteCopy(Copy e) {
        if (e == null){
            throw new NotNullException("Copy", "null");
        } if (copyRepository.BFindById(e.getId()) == null){
            throw new NotFoundException("Copy", e.getId());
        }
        copyRepository.BDelete(e.getId());
        return true;
    }

    public Copy getCopyById(String id)  {
        if (id == null){
            throw new NotNullException("Copy", "null");
        }
        if(!isValidIdFormat(id)) {
            throw new BadFormatException("Copy ID", id);
        }
        Copy copy = (Copy)copyRepository.BFindById(id);
        if (copy == null){
            throw new NotFoundException("Copy", id);
        }
        return copy;
    }
    private boolean isValidIdFormat(String id) {
        return id.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    }
    public List<?> findAllCopies(){
        return copyRepository.BFindAll();
    }

    public boolean updateCopies(Copy e) {
        try {
            Copy oldCopy = (Copy)copyRepository.BFindById(e.getId());
            if (oldCopy == null) {
                throw new NotFoundException("Copy", e.getId());
            } else {
                if (e.getBook() == null || e.getState() == null || e.getDisponibility() == null || e.getBarCode() == null) {
                    throw new BadObjectException("Copy", "Required fields are missing");
                }
                if (e.getState() != CopyState.DAMAGED || e.getState() != CopyState.GOOD_CONDITION || e.getState() != CopyState.FAIR) {
                    throw new BadStateException("Copy", e.getState().name());
                }
                if (e.getDisponibility() != CopyDispo.AVAILABLE || e.getDisponibility() != CopyDispo.BORROWED) {
                    throw new BadAvailabilityException("Copy",e.getDisponibility().name());
                }
            }
            copyRepository.BUpdate(e);
            return true;
        } catch (IllegalArgumentException ex){
            throw new BadValuesException("Copy", e.getId());
        }
    }

    public List<Copy> findCopiesByBook(Book book)  {
        if(book == null){
            throw new NotNullException("Copy", "null");
        }
        List<Copy> copies = ((CopyRepository) copyRepository).findCopyByBook(book);
        if(copies == null || copies.isEmpty()){
            throw new NotFoundException("Copy","for book with ID"+book.getBookId());
        }
        return copies;
    }
    public Copy findCopyByBarcode(String barcode) {
        if(barcode == null){
            throw new NotNullException("Barcode", "null");
        }
        Copy copy = ((CopyRepository) copyRepository).findCopyByBarCode(barcode);
        if (copy == null){
            throw new NotFoundException("Copy","with barcode" +barcode);
        }
        return copy;
    }

}
