package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.OrderProduct;
import lombok.Data;

@Data
public class OrderProductDto {
	
	private Integer amount;
	
	private Double price;
	
	private OrderDto order;
	
	private ProductDto product;
	
	
	public OrderProductDto(OrderProduct orderProduct) {
		this.amount = orderProduct.getAmount();
		this.price = orderProduct.getPrice();
		this.order = new OrderDto(orderProduct.getOrderOrderProduct());
		this.product = new ProductDto(orderProduct.getProductOrderProduct());
	}

}
