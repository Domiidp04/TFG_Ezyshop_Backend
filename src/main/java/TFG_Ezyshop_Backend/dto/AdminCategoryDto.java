package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminCategoryDto extends CategoryDto{

	private Long id;
	
	private Boolean disabled;

	public AdminCategoryDto(Category category) {
		super(category);
		this.id = category.getId();
		this.disabled = category.getDisabled();
	}
	
	
}
