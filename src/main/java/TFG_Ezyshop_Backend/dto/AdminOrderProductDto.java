package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.OrderProduct;
import lombok.Data;

@Data
public class AdminOrderProductDto {

	private Long id;
	
	private Integer amount;
	
	private Double price;
	
	private AdminOrderDto order;
	
	private AdminProductDto product;
	
	public AdminOrderProductDto(OrderProduct orderProduct) {
		this.id = orderProduct.getId();
		this.amount = orderProduct.getAmount();
		this.price = orderProduct.getPrice();
		this.order = new AdminOrderDto(orderProduct.getOrderOrderProduct());
		this.product = new AdminProductDto(orderProduct.getProductOrderProduct());
	}
}
