package edu.eci.cvds.Books.Controller;


import edu.eci.cvds.Books.Controller.RequestModel.SubcategoryRequest;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
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
    public ResponseEntity<?> createSubcategory(@RequestParam String categoryId,@RequestBody SubcategoryRequest subcategoryRequest){
        try{
            Subcategory subcategory = new Subcategory(subcategoryRequest.getBook(),
                    subcategoryRequest.getSubcategoryId(),
                    subcategoryRequest.getCategory(),
                    subcategoryRequest.getDescription(),
                    subcategoryRequest.isActive()
            );

            subcategoryService.createSubcategory(categoryId,subcategory);
            return new ResponseEntity<>(subcategory.getSubcategoryId(), HttpStatus.OK);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteSubcategory")
    public ResponseEntity<?> deleteSubcategory(@RequestParam String id){
        try{
            subcategoryService.deleteSubcategory(id);
            return new ResponseEntity<>("Subcategory deleted successfully",HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateSubcategory")
    public ResponseEntity<?> updateSubcategory(@RequestBody SubcategoryRequest subcategoryRequest){
        try{
            Subcategory subcategory = new Subcategory(subcategoryRequest.getBook(),
                    subcategoryRequest.getSubcategoryId(),
                    subcategoryRequest.getCategory(),
                    subcategoryRequest.getDescription(),
                    subcategoryRequest.isActive()
            );

            subcategoryService.updateSubcategory(subcategory);
            return new ResponseEntity<>("Subcategory updated successfully",HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getSubcategory")
    public ResponseEntity<?> getSubcategory(@RequestParam String id){
        try{
            Subcategory subcategory = subcategoryService.getSubcategory(id);
            return new ResponseEntity<>(subcategory, HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
