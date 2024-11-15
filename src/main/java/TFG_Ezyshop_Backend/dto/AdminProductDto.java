package TFG_Ezyshop_Backend.dto;

import java.util.List;
import java.util.stream.Collectors;

import TFG_Ezyshop_Backend.entities.Product;
import lombok.Data;

@Data
public class AdminProductDto {

	private Long id;
	
	private String title;
	
	private String description;
	
	private Double price;
	
	private Double discountPrice;
	
	private Integer stock;
	
	private Boolean disabled;
	
	private AdminCategoryDto category;
	
	private List<AdminImageProductDto> imageProducts;
	
	private List<AdminAssessmentDto> assessments;
	
	public AdminProductDto(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.discountPrice = product.getDiscountPrice();
		this.stock = product.getStock();
		this.disabled = product.getDisabled();
		this.category = new AdminCategoryDto(product.getCategoryProduct());
		this.imageProducts = product.getImageProducts().stream()
		        .map(AdminImageProductDto::new)
		        .collect(Collectors.toList());
		this.assessments = product.getAssessments().stream()
		        .map(AdminAssessmentDto::new)
		        .collect(Collectors.toList());
		
	}
	
}
