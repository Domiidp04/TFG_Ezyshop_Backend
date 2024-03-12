package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Entity.Category;
import TFG_Ezyshop_Backend.Repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<Category> getAll(){
		return categoryRepository.findAll();
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
