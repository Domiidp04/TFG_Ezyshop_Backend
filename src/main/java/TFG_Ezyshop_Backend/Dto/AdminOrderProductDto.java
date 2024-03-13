package TFG_Ezyshop_Backend.Dto;

import TFG_Ezyshop_Backend.Entity.OrderProduct;
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
