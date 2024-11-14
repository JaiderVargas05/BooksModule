package edu.eci.cvds.Books.Controller;


import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/CategoryModule")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(@Qualifier("ImpCat") CategoryService categoryService){
        this.categoryService=categoryService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        try{
            categoryService.createCategory(category);
            return new ResponseEntity<>(category.getCategoryId(), HttpStatus.OK);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<?> deleteCategory(@RequestParam String id){
        try{
            categoryService.deleteCategory(id);
            return new ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateCategory")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        try{
            categoryService.updateCategory(category);
            return new ResponseEntity<>("Category updated successfully",HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getCategory")
    public ResponseEntity<?> getCategory(@RequestParam String id){
        try{
            Category category = categoryService.getCategory(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getSubcategories")
    public ResponseEntity<?> getSubcategories(@RequestParam String id){
        try{
            List<?> subcategories = categoryService.getSubcategories(id);
            return new ResponseEntity<>(subcategories, HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
