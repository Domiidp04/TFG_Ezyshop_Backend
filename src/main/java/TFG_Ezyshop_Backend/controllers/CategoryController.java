package TFG_Ezyshop_Backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.entities.Category;
import TFG_Ezyshop_Backend.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<?> categories = categoryService.getAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Optional<?> category = categoryService.getById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category newCategory) {
        Optional<Category> updatedCategory = categoryService.update(id, newCategory);
        if (updatedCategory.isPresent()) {
            return new ResponseEntity<>(updatedCategory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Boolean isDeleted = categoryService.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Categoría con Id : (" + id + ") no encontrada", HttpStatus.NOT_FOUND);
        }
    }
}
