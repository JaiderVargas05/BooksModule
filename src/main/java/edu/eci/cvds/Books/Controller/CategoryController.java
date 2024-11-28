package edu.eci.cvds.Books.Controller;


import edu.eci.cvds.Books.Controller.RequestModel.CategoryRequest;
import edu.eci.cvds.Books.Controller.ResponseModel.CategoryResponse;
import edu.eci.cvds.Books.Controller.ResponseModel.SubcategoryResponse;
import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Domain.Category;
import edu.eci.cvds.Books.Domain.Subcategory;
import edu.eci.cvds.Books.Exception.*;
import edu.eci.cvds.Books.Repository.Model.BasicBook;
import edu.eci.cvds.Books.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/CategoryModule")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(@Qualifier("ImpCat") CategoryService categoryService){
        this.categoryService=categoryService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/createCategory")
    public Object createCategory(@RequestBody CategoryRequest categoryRequest){
        try{
            Category category = new Category(categoryRequest.getCategoryId(),
                    categoryRequest.getDescription(),
                    categoryRequest.isActive());

            String id = categoryService.createCategory(category);
            return new CategoryResponse(HttpStatus.OK,CategoryResponse.SUCCESS_CATEGORY_SAVED,id);
        }catch (BadRequestException e){
            return new CategoryResponse(HttpStatus.BAD_REQUEST, e.getMessage(), Collections.emptyList());
        }catch (DuplicateObjectException e){
            return new CategoryResponse(HttpStatus.CONFLICT, e.getMessage(),Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),  Collections.emptyList());
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteCategory")
    public CategoryResponse deleteCategory(@RequestParam String id){
        try{
            categoryService.deleteCategory(id);
            return new CategoryResponse(HttpStatus.OK, CategoryResponse.SUCCESS_CATEGORY_DELETED,Collections.emptyList());
        }catch(NotFoundException e){
            return new CategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new CategoryResponse(HttpStatus.BAD_REQUEST, e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),  Collections.emptyList());

        }
    }
    @CrossOrigin(origins = "*")
    @PatchMapping ("/updateCategory")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest){
        try{
            Category category = new Category(categoryRequest.getCategoryId(),
                    categoryRequest.getDescription(),
                    categoryRequest.isActive()
            );
            categoryService.updateCategory(category);
            return new CategoryResponse(HttpStatus.OK, CategoryResponse.SUCCESS_CATEGORY_UPDATED,category);
        }catch(NotFoundException e){
            return new CategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        } catch (BadRequestException e){
            return new CategoryResponse(HttpStatus.BAD_REQUEST, e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),  Collections.emptyList());

        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getCategory")
    public CategoryResponse getCategory(@RequestParam String id){
        try{
            Category category = categoryService.getCategory(id);
            return new CategoryResponse(HttpStatus.OK, CategoryResponse.SUCCESS_CATEGORY_RETRIEVED,category);
        }catch(NotFoundException e){
            return new CategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new CategoryResponse(HttpStatus.BAD_REQUEST, e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),  Collections.emptyList());

        }
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/getCategories")
    public CategoryResponse getCategories(){
        try{
            List<?> categories = categoryService.getCategories();
            return new CategoryResponse(HttpStatus.OK, CategoryResponse.SUCCESS_CATEGORY_RETRIEVED, (List<Category>) categories);
        }catch(NotFoundException e){
            return new CategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new CategoryResponse(HttpStatus.BAD_REQUEST, e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),  Collections.emptyList());

        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/getBooks")
    public CategoryResponse getBooks(@RequestParam String idCategory){
        try{
            List<?> books = categoryService.getBooks(idCategory);
            return new CategoryResponse(HttpStatus.OK, CategoryResponse.SUCCESS, (List<?>) books);
        }catch(NotFoundException e){
            return new CategoryResponse(HttpStatus.NOT_FOUND,e.getMessage(),Collections.emptyList());
        }catch (BadRequestException e){
            return new CategoryResponse(HttpStatus.BAD_REQUEST, e.getMessage(), Collections.emptyList());
        } catch (InternalServerErrorException e){
            return new CategoryResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),  Collections.emptyList());

        }
    }


}
