package edu.eci.cvds.Books.Controller;


import edu.eci.cvds.Books.Domain.Copy;
import edu.eci.cvds.Books.Exception.CopyException;
import edu.eci.cvds.Books.Service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<?> createCopy(@RequestParam String bookId, @RequestBody Copy copy){
        try{
            copyService.createCopy(bookId, copy);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCopy(@RequestBody Copy copy){
        try{
            copyService.deleteCopy(copy);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getCopy")
    public ResponseEntity<?> getCopy(@RequestParam String id){
        try{
            Copy copy = copyService.getCopyById(id);
            return new ResponseEntity<>(copy, HttpStatus.OK);
        }catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/findAll")
    public ResponseEntity<?> findCopies(){
        try{
            List<?> copies = copyService.findAllCopies();
            return new ResponseEntity<>(copies, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/update")
    public ResponseEntity<?> updateCopy(Copy copy){
        try{
            copyService.updateCopies(copy);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/findByBarCode")
    public ResponseEntity<?> findCopyByBarcode(String barcode){
        try{
            Copy copy = copyService.findCopyByBarcode(barcode);
            return new ResponseEntity<>(copy, HttpStatus.OK);
        } catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
