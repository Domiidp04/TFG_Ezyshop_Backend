package TFG_Ezyshop_Backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.Dto.AdminCategoryDto;
import TFG_Ezyshop_Backend.Services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<AdminCategoryDto>> getAll(){
		return new ResponseEntity<List<AdminCategoryDto>>(categoryService.getAll(), HttpStatus.OK);
	}
}
