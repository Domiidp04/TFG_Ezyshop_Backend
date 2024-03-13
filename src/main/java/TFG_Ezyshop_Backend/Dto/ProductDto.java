package TFG_Ezyshop_Backend.Dto;

import TFG_Ezyshop_Backend.Entity.Product;
import lombok.Data;

@Data
public class ProductDto {
	
	private String title;
	
	private String description;
	
	private Double price;
	
	private Double discount_price;
	
	private Integer stock;
	
	private CategoryDto category;
	
	public ProductDto(Product product) {
		this.title = product.getTitle();
		this.description = product.getDescription();
		this.price = product.getPrice();
		//Validamos que si el precio de descuento es null o no | NullPointException
		if (product.getDiscountPrice() == null) {
			this.discount_price = null;
		} else {
			this.discount_price = product.getDiscountPrice();
		}
		this.stock = product.getStock();
		this.category = new CategoryDto(product.getCategoryProduct());
		
	}

}
