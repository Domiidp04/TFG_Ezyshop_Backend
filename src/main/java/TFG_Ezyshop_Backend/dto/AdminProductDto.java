package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.Product;
import lombok.Data;

@Data
public class AdminProductDto {

	private Long id;
	
	private String title;
	
	private String description;
	
	private Double price;
	
	private Double discount_price;
	
	private Integer stock;
	
	private Boolean disabled;
	
	private AdminCategoryDto category;
	
	public AdminProductDto(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.discount_price = product.getDiscountPrice();
		this.stock = product.getStock();
		this.disabled = product.getDisabled();
//		this.category = new AdminCategoryDto(product.getCategoryProduct());
		
	}
	
}
