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

@RestController
@RequestMapping("/CopyModule")
public class CopyController {
    private final CopyService copyService;
    @Autowired
    public CopyController(@Qualifier("ejemImp") CopyService copyService) {
        this.copyService = copyService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/CreateCopy")
    public ResponseEntity<?> createEjemplar(@RequestBody Copy copy){
        try{
            copyService.createEjemplar(copy);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/DeleteEjemplar")
    public ResponseEntity<?> deleteEjemplar(@RequestBody Copy copy){
        try{
            copyService.deleteEjemplar(copy);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getEjemplar")
    public ResponseEntity<?> getEjemplar(@RequestParam String id){
        try{
            copyService.getEjemplarById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/findAll")
    public ResponseEntity<?> findEjemplars(){
        try{
            List<?> copies = copyService.findAllEjemplars();
            return new ResponseEntity<>(copies, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PutMapping ("/update")
    public ResponseEntity<?> updateEjemplar(Copy copy){
        try{
            copyService.updateEjemplar(copy);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/findByBarCode")
    public ResponseEntity<?> findEjemplarByBarcode(String barcode){
        try{
            Copy copy = copyService.findEjemplarByBarcode(barcode);
            return new ResponseEntity<>(copy, HttpStatus.OK);
        } catch (CopyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
