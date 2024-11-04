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
}
