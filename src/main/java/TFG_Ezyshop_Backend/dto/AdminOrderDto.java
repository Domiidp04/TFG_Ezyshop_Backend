package TFG_Ezyshop_Backend.dto;

import java.util.Date;

import TFG_Ezyshop_Backend.entities.Order;
import lombok.Data;

@Data
public class AdminOrderDto {

	private Long id;

	private Double totalAmount;

	private Double shippingAmount;

	private Double savedAmount;

	private Double paymentAmount;

	private Date orderDate;

	private Date shippingDate;

	private String discount;

	private String intent;

	private String currency;

	private String method;
	
	private Boolean payment;

	private AdminUserDto user;

	public AdminOrderDto(Order order) {
		this.id = order.getId();
		this.totalAmount = order.getTotalAmount();
		this.shippingAmount = order.getShippingAmount();
		this.savedAmount = order.getSavedAmount();
		this.paymentAmount = order.getPaymentAmount();
		this.orderDate = order.getOrderDate();
		this.shippingDate = order.getShippingDate();
		this.discount = order.getDiscountOrder() != null ? this.discount = order.getDiscountOrder().getCode() : null;
		// Validamos el null del codigo de descuento
		if (order.getDiscountOrder() == null) {
			this.discount = null;
		} else {
			this.discount = order.getDiscountOrder().getCode();
		}

		this.currency = order.getCurrency();
		this.method = order.getMethod();
		this.intent = order.getIntent();
		this.payment = order.getPayment();
		this.user = new AdminUserDto(order.getUserOrder());
	}
}
