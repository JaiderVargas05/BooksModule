package edu.eci.cvds.Books.Controller;


import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Ejemplar;
import edu.eci.cvds.Books.Exception.EjemplarException;
import edu.eci.cvds.Books.Service.EjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/EjemplarModule")
public class EjemplarController {
    private final EjemplarService ejemplarService;
    @Autowired
    public EjemplarController(@Qualifier("ejemImp") EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/CreateEjemplar")
    public ResponseEntity<?> createEjemplar(@RequestBody Ejemplar ejemplar){
        try{
            ejemplarService.createEjemplar(ejemplar);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EjemplarException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/DeleteEjemplar")
    public ResponseEntity<?> deleteEjemplar(@RequestBody Ejemplar ejemplar){
        try{
            ejemplarService.deleteEjemplar(ejemplar);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EjemplarException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getEjemplar")
    public ResponseEntity<?> getEjemplar(@RequestParam String id){
        try{
            ejemplarService.getEjemplarById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EjemplarException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/findAll")
    public ResponseEntity<?> findEjemplars(){
        try{
            List<Ejemplar> ejemplars = ejemplarService.findAllEjemplars();
            return new ResponseEntity<>(ejemplars, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PutMapping ("/update")
    public ResponseEntity<?> updateEjemplar(Ejemplar ejemplar){
        try{
            ejemplarService.updateEjemplar(ejemplar);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EjemplarException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/findByBarCode")
    public ResponseEntity<?> findEjemplarByBarcode(Integer barcode){
        try{
            Ejemplar ejemplar = ejemplarService.findEjemplarByBarcode(barcode);
            return new ResponseEntity<>(ejemplar, HttpStatus.OK);
        } catch (EjemplarException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
