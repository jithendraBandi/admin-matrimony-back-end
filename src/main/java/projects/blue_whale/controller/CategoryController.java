package projects.blue_whale.controller;

import projects.blue_whale.dto.ApiResponse;
import projects.blue_whale.entity.Category;
import projects.blue_whale.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.exceptions.CustomException;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody Category category) throws CustomException {
        categoryService.saveCategory(category);
        return new ResponseEntity<>(new ApiResponse("Category updated successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        return new ResponseEntity<>(new ApiResponse(categoryList), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully."), HttpStatus.OK);
    }
}
