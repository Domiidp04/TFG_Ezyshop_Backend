package TFG_Ezyshop_Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category , Long>{

//	public List<Category> getAll();
//	public Optional<Category> getCategory(Long categoryId);
//	public Category create(Category category); 
//	public void delete(Long categoryId);
	
}
