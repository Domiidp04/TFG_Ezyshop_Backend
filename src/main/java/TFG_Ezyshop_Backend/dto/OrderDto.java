package TFG_Ezyshop_Backend.dto;

import java.util.Date;
import java.util.List;

import TFG_Ezyshop_Backend.entities.Order;
import lombok.Data;

@Data
public class OrderDto {
	
	private Long id;

	private Double totalAmount;
	
	private Double shippingAmount;
	
	private Double savedAmount;
	
	private Double paymentAmount;
	
	private Date orderDate;
	
	private Date shippingDate;
	
	private String discount;
	
	private Boolean payment;
	
	private UserDto user;

	private List<OrderProductDto> lineas;
	
	
	public OrderDto(Order order) {
		this.id = order.getId();
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
		
		this.payment = order.getPayment();
		
		this.user = new UserDto(order.getUserOrder());
		this.lineas = order.getOrders().stream().map(OrderProductDto::new).toList();
	}
	
}
