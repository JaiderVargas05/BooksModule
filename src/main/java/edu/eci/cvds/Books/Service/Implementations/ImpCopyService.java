package edu.eci.cvds.Books.Service.Implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Codes.CodeGenerator;
import edu.eci.cvds.Books.Codes.GenerateCodeException;
import edu.eci.cvds.Books.Controller.RequestModel.BookRequest;
import edu.eci.cvds.Books.Controller.RequestModel.CopyRequest;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Domain.CopyDispo;
import edu.eci.cvds.Books.Domain.CopyState;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.BRepository;
import edu.eci.cvds.Books.Repository.BookRepository;
import edu.eci.cvds.Books.Repository.CopyRepository;
import edu.eci.cvds.Books.Service.CopyService;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.TransientObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Override
    public String createCopyByIsbn(CopyRequest copyRequest){
        try{

            if (copyRequest.getIsbn() == null){
                throw new NotNullException("isbn", "null");
            }

            if(copyRequest.getState() == null){
                throw new BadStateException("state", "null");
            }
            Book book = (Book) ((BookRepository)bookRepository).findByIsbn(copyRequest.getIsbn());

            if (book == null){
                throw new NotFoundException("Copy", copyRequest.getIsbn());
            }
            Copy copy = new Copy(book,copyRequest.getState(),copyRequest.getUbication());
            copy.setBook(book);
            copyRepository.BSave(copy);
            String barcode = codeGenerator.generateCode(copy.getId());
            copy.setBarCode(barcode);
            copyRepository.BSave(copy);

            return copy.getId();
        } catch (IllegalArgumentException ex){
            throw new BadStateException("Copy", copyRequest.getIsbn());
        } catch (TransientObjectException | GenerateCodeException ex){
            throw new BadObjectException("Copy", copyRequest.getIsbn());
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
//                if (e.getState() != CopyState.DAMAGED || e.getState() != CopyState.GOOD_CONDITION || e.getState() != CopyState.FAIR) {
//                    throw new BadStateException("Copy", e.getState().name());
//                }
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
    public List<ObjectNode> saveCopies(MultipartFile file){
        List<ObjectNode> jsonList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Leer la primera hoja
            Row headerRow = sheet.getRow(0); // Primera fila como encabezados
            List<String> headers = new ArrayList<>();

            // Leer encabezados
            headerRow.forEach(cell -> headers.add(cell.getStringCellValue()));

            // Iterar sobre las filas de datos
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la primera fila de encabezados

                // Crear el objeto BookRequest
                CopyRequest copyRequest = new CopyRequest();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String header = headers.get(j); // Obtenemos el nombre del encabezado que es el nombre del campo

                    switch (cell.getCellType()) {
                        case STRING:
                            String stringValue = cell.getStringCellValue();
                            // Asignar el valor del campo en BookRequest basado en el encabezado
                            if ("isbn".equalsIgnoreCase(header)) {
                                copyRequest.setIsbn(stringValue);
                            } else if ("state".equalsIgnoreCase(header)) {
                                copyRequest.setState(stringValue);
                            } else if ("ubication".equalsIgnoreCase(header)) {
                                copyRequest.setUbication(stringValue);
                            }
                            break;

                        default:
                            break;
                    }
                }

                try{
                    String copyId = this.createCopyByIsbn(copyRequest);
                } catch (Exception e) {
                    ObjectNode errorNode = objectMapper.createObjectNode();
                    errorNode.put("error", e.getMessage());
                    errorNode.put("isbn", copyRequest.getIsbn());
                    errorNode.put("state", copyRequest.getState());
                    errorNode.put("ubication", copyRequest.getUbication());
                    jsonList.add(errorNode);
                }

            }

        } catch (InvalidFormatException e) {
            throw new RuntimeException("El formato del archivo Excel no es válido", e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo Excel", e);
        } catch (Exception e) {
            throw new RuntimeException("Error procesando el archivo Excel", e);
        }

        return jsonList;
    }

}
