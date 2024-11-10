package edu.eci.cvds.Books.Controller;

import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.CategoryException;
import edu.eci.cvds.Books.Exception.SubcategoryException;
import edu.eci.cvds.Books.Service.CategoryService;
import edu.eci.cvds.Books.Service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/SubcategoryModule")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    @Autowired
    public SubcategoryController(@Qualifier("ImpSub") SubcategoryService subcategoryService){
        this.subcategoryService=subcategoryService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/createSubcategory")
    public ResponseEntity<?> createSubcategory(@RequestBody Subcategory subcategory){
        try{
            subcategoryService.createSubcategory(subcategory);
            return new ResponseEntity<>(subcategory.getSubcategoryId(), HttpStatus.OK);
        }catch (SubcategoryException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteSubcategory")
    public ResponseEntity<?> deleteSubcategory(@RequestParam String id){
        try{
            subcategoryService.deleteSubcategory(id);
            return new ResponseEntity<>("Subcategory deleted successfully",HttpStatus.OK);
        }catch (SubcategoryException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateSubcategory")
    public ResponseEntity<?> updateSubcategory(@RequestBody Subcategory subcategory){
        try{
            subcategoryService.updateSubcategory(subcategory);
            return new ResponseEntity<>("Subcategory updated successfully",HttpStatus.OK);
        } catch (SubcategoryException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getSubcategory")
    public ResponseEntity<?> getSubcategory(@RequestParam String id){
        try{
            Subcategory subcategory = subcategoryService.getSubcategory(id);
            return new ResponseEntity<>(subcategory, HttpStatus.OK);
        }catch (SubcategoryException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
