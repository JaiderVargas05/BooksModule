package edu.eci.cvds.Books.Controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.cvds.Books.Controller.RequestModel.CopyRequest;
import edu.eci.cvds.Books.Controller.ResponseModel.BookResponse;
import edu.eci.cvds.Books.Controller.ResponseModel.CopyResponse;
import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Exception.BadRequestException;
import edu.eci.cvds.Books.Exception.InternalServerErrorException;
import edu.eci.cvds.Books.Exception.NotFoundException;
import edu.eci.cvds.Books.Service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/CopyModule")
public class CopyController {
    private final CopyService copyService;
    @Autowired
    public CopyController(@Qualifier("CopyImp") CopyService copyService) {
        this.copyService = copyService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/createCopy")
    public CopyResponse createCopy(@RequestParam String bookId, @RequestBody CopyRequest copyRequest){
        try{
            Copy copy = new Copy(copyRequest.getId(),
                    copyRequest.getBook(),
                    copyRequest.getState(),
                    copyRequest.getBarCode(),
                    copyRequest.getDisponibility(),
                    copyRequest.isActive()
            );

            String id = copyService.createCopy(bookId, copy);
            return new CopyResponse(HttpStatus.OK,CopyResponse.SUCCESS_COPY_SAVED,id);
        }catch (BadRequestException e){
            return new CopyResponse(HttpStatus.BAD_REQUEST,e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CopyResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), Collections.emptyList());
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete")
    public CopyResponse deleteCopy(@RequestBody CopyRequest copyRequest){
        try{
            Copy copy = new Copy(copyRequest.getId(),
                    copyRequest.getBook(),
                    copyRequest.getState(),
                    copyRequest.getBarCode(),
                    copyRequest.getDisponibility(),
                    copyRequest.isActive()
            );

            copyService.deleteCopy(copy);

            return new CopyResponse(HttpStatus.OK,CopyResponse.SUCCESS_COPY_DELETED, copy);
        }catch(NotFoundException e){
            return new CopyResponse(HttpStatus.NOT_FOUND,e.getMessage(), Collections.emptyList());
        }catch (BadRequestException e){
            return new CopyResponse(HttpStatus.BAD_REQUEST,e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CopyResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), Collections.emptyList());
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getCopy")
    public CopyResponse getCopy(@RequestParam String id){
        try{
            Copy copy = copyService.getCopyById(id);
            return new CopyResponse(HttpStatus.OK,CopyResponse.SUCCESS_COPY_RETRIEVED, copy);
        }catch(NotFoundException e){
            return new CopyResponse(HttpStatus.NOT_FOUND,e.getMessage(), Collections.emptyList());
        }catch (BadRequestException e){
            return new CopyResponse(HttpStatus.BAD_REQUEST,e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CopyResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), Collections.emptyList());
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/findAll")
    public CopyResponse findCopies(){
        try{
            List<?> copies = copyService.findAllCopies();
            return new CopyResponse(HttpStatus.OK,CopyResponse.SUCCESS_COPY_RETRIEVED, (List<Copy>) copies);
        } catch(NotFoundException e){
            return new CopyResponse(HttpStatus.NOT_FOUND,e.getMessage(), Collections.emptyList());
        }catch (InternalServerErrorException e){
            return new CopyResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), Collections.emptyList());
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/update")
    public CopyResponse updateCopy(@RequestBody CopyRequest copyRequest){
        try{


            copyService.updateCopies(copyRequest);
            return new CopyResponse(HttpStatus.OK,CopyResponse.SUCCESS_COPY_UPDATED, copyRequest.getId() );

        }catch(NotFoundException e){
            return new CopyResponse(HttpStatus.NOT_FOUND,e.getMessage(), Collections.emptyList());
        } catch (BadRequestException e){
            return new CopyResponse(HttpStatus.BAD_REQUEST,e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CopyResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/findByBarCode")
    public CopyResponse findCopyByBarcode(String barcode){
        try{
            Copy copy = copyService.findCopyByBarcode(barcode);
            return new CopyResponse(HttpStatus.OK,CopyResponse.SUCCESS_COPY_RETRIEVED, copy);
        }catch(NotFoundException e){
            return new CopyResponse(HttpStatus.NOT_FOUND,e.getMessage(), Collections.emptyList());
        } catch (BadRequestException e){
            return new CopyResponse(HttpStatus.BAD_REQUEST,e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CopyResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), Collections.emptyList());
        }
    }

    @CrossOrigin("*")
    @PostMapping("/saveCopies")
    public BookResponse saveCopies(@RequestParam("file") MultipartFile file) {
        try{
            List<ObjectNode> badCopies = copyService.saveCopies(file);
            return new BookResponse(HttpStatus.OK,BookResponse.SUCCESS_BOOK_SAVED,badCopies);
        }catch (BadRequestException e){
            return new BookResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new BookResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());
        }
    }

}
