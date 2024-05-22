package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.Category;
import lombok.Data;

@Data
public class CategoryDto {
	
	Long id;

	private String title;
	
	private String description;

	public CategoryDto(Category category) {
		this.id = category.getId();
		this.title = category.getTitle();
		this.description = category.getDescription();
	}
	
	
}
