package edu.eci.cvds.Books.Controller;


import edu.eci.cvds.Books.Controller.RequestModel.SubcategoryRequest;
import edu.eci.cvds.Books.Controller.ResponseModel.SubcategoryResponse;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public SubcategoryResponse createSubcategory(@RequestParam String categoryId, @RequestBody SubcategoryRequest subcategoryRequest){
        try{
            Subcategory subcategory = new Subcategory(subcategoryRequest.getBook(),
                    subcategoryRequest.getSubcategoryId(),
                    subcategoryRequest.getCategory(),
                    subcategoryRequest.getDescription(),
                    subcategoryRequest.isActive()
            );
            String id = subcategoryService.createSubcategory(categoryId,subcategory);
            return new SubcategoryResponse(HttpStatus.OK,SubcategoryResponse.SUCCESS_SUBCATEGORY_SAVED,id);
        }catch (BadRequestException e){
            return new SubcategoryResponse(HttpStatus.BAD_REQUEST,e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new SubcategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), Collections.emptyList());
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteSubcategory")
    public SubcategoryResponse deleteSubcategory(@RequestParam String id){
        try{
            subcategoryService.deleteSubcategory(id);
            return new SubcategoryResponse(HttpStatus.OK,SubcategoryResponse.SUCCESS_SUBCATEGORY_DELETED,Collections.emptyList());
        }catch(NotFoundException e){
            return new SubcategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new SubcategoryResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new SubcategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());
        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateSubcategory")
    public SubcategoryResponse updateSubcategory(@RequestBody SubcategoryRequest subcategoryRequest){
        try{
            Subcategory subcategory = new Subcategory(subcategoryRequest.getBook(),
                    subcategoryRequest.getSubcategoryId(),
                    subcategoryRequest.getCategory(),
                    subcategoryRequest.getDescription(),
                    subcategoryRequest.isActive()
            );
            subcategoryService.updateSubcategory(subcategory);
            return new SubcategoryResponse(HttpStatus.OK,SubcategoryResponse.SUCCESS_SUBCATEGORY_UPDATED,subcategory);
        }catch(NotFoundException e){
            return new SubcategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        } catch (BadRequestException e){
            return new SubcategoryResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new SubcategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getSubcategory")
    public SubcategoryResponse getSubcategory(@RequestParam String id){
        try{
            Subcategory subcategory = subcategoryService.getSubcategory(id);
            return new SubcategoryResponse(HttpStatus.OK,SubcategoryResponse.SUCCESS_SUBCATEGORY_RETRIEVED,subcategory);
        }catch(NotFoundException e){
            return new SubcategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new SubcategoryResponse(HttpStatus.BAD_REQUEST,e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new SubcategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),Collections.emptyList());

        }
    }

}
