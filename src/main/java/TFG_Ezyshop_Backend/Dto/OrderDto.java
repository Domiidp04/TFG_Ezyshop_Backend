package TFG_Ezyshop_Backend.Dto;

import java.util.Date;

import TFG_Ezyshop_Backend.Entity.Order;
import lombok.Data;

@Data
public class OrderDto {

	private Double totalAmount;
	
	private Double shippingAmount;
	
	private Double savedAmount;
	
	private Double paymentAmount;
	
	private Date orderDate;
	
	private Date shippingDate;
	
	private String discount;
	
	private UserDto user;
	
	public OrderDto(Order order) {
		this.totalAmount = order.getTotalAmount();
		this.shippingAmount = order.getShippingAmount();
		this.savedAmount = order.getSavedAmount();
		this.paymentAmount = order.getPaymentAmount();
		this.orderDate = order.getOrderDate();
		this.shippingDate = order.getShippingDate();
		
		//Validamos el null del codigo de descuento
		if (order.getDiscountOrder() == null) {
			this.discount = null;
		} else {
			this.discount = order.getDiscountOrder().getCode();
		}
		
		this.user = new UserDto(order.getUserOrder());
	}
	
}