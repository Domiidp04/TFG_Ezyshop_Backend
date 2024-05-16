package TFG_Ezyshop_Backend.dto;

import java.util.List;
import java.util.stream.Collectors;

import TFG_Ezyshop_Backend.entities.Product;
import lombok.Data;

@Data
public class ProductDto {
	
	private Long id;

	private String title;
	
	private String description;
	
	private Double price;
	
	private Double discountPrice;
	
	private Integer stock;
	
	private CategoryDto category;
	
	private List<ImageProductDto> imageProducts;
	
	private List<AssessmentDto>assessments;
	
	
	public ProductDto(Product product) {
		this.setId(product.getId());
		this.title = product.getTitle();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.discountPrice = product.getDiscountPrice();
		this.stock = product.getStock();
		this.category = new CategoryDto(product.getCategoryProduct());
		this.imageProducts = product.getImageProducts().stream()
		        .map(ImageProductDto::new)
		        .collect(Collectors.toList());
		this.assessments = product.getAssessments().stream()
		        .map(AssessmentDto::new)
		        .collect(Collectors.toList());
	}
	
	
	
	
}
