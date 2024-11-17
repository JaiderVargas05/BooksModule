package edu.eci.cvds.Books.Controller;


import edu.eci.cvds.Books.Controller.RequestModel.CopyRequest;
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
    public ResponseEntity<?> createCopy(@RequestParam String bookId, @RequestBody CopyRequest copyRequest){
        try{
            Copy copy = new Copy(copyRequest.getId(),
                    copyRequest.getBook(),
                    copyRequest.getState(),
                    copyRequest.getBarCode(),
                    copyRequest.getDisponibility(),
                    copyRequest.isActive()
            );

            copyService.createCopy(bookId, copy);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCopy(@RequestBody CopyRequest copyRequest){
        try{
            Copy copy = new Copy(copyRequest.getId(),
                    copyRequest.getBook(),
                    copyRequest.getState(),
                    copyRequest.getBarCode(),
                    copyRequest.getDisponibility(),
                    copyRequest.isActive()
            );

            copyService.deleteCopy(copy);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getCopy")
    public ResponseEntity<?> getCopy(@RequestParam String id){
        try{
            Copy copy = copyService.getCopyById(id);
            return new ResponseEntity<>(copy, HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/findAll")
    public ResponseEntity<?> findCopies(){
        try{
            List<?> copies = copyService.findAllCopies();
            return new ResponseEntity<>(copies, HttpStatus.OK);
        } catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/update")
    public ResponseEntity<?> updateCopy(@RequestBody CopyRequest copyRequest){
        try{
            Copy copy = new Copy(copyRequest.getId(),
                    copyRequest.getBook(),
                    copyRequest.getState(),
                    copyRequest.getBarCode(),
                    copyRequest.getDisponibility(),
                    copyRequest.isActive()
            );

            copyService.updateCopies(copy);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/findByBarCode")
    public ResponseEntity<?> findCopyByBarcode(String barcode){
        try{
            Copy copy = copyService.findCopyByBarcode(barcode);
            return new ResponseEntity<>(copy, HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
