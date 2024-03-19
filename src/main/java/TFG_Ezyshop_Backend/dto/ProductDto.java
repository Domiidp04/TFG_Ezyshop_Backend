package TFG_Ezyshop_Backend.dto;

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
	
	private Boolean disabled;
	
	private CategoryDto category;
	
	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.discountPrice = product.getDiscountPrice();
		this.stock = product.getStock();
		this.disabled = product.getDisabled();
		this.category = new CategoryDto(product.getCategoryProduct());
	}
	
	
}
