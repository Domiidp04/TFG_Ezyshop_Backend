package TFG_Ezyshop_Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminCategoryDto;
import TFG_Ezyshop_Backend.entities.Category;
import TFG_Ezyshop_Backend.repositories.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<AdminCategoryDto> getAll(){
		List<Category>categories = categoryRepository.findAll();
		return categories.stream()
				.map(AdminCategoryDto::new)
				.collect(Collectors.toList());
	}
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	public Boolean delete(Long categoryId) {
		return getCategory(categoryId).map(category -> {
			categoryRepository.deleteById(categoryId);
		return true;
		}).orElse(false);
	}
	
	public Optional<Category> getCategory(Long categoryId){
		return categoryRepository.findById(categoryId);
	}
	
	
}
