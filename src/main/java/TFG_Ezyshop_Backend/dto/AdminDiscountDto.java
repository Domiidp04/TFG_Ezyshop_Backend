package TFG_Ezyshop_Backend.dto;

import java.util.Date;
import java.util.List;

import TFG_Ezyshop_Backend.entities.Discount;
import TFG_Ezyshop_Backend.entities.Order;
import lombok.Data;

@Data
public class AdminDiscountDto {
	
	private Long id;
	
	private String title;
	
	private String code;
	
	private Date startDate;
	
	private Date finalDate;
	
	private Boolean expired;
	
	private Integer use;
	
	private List<Order> orders;

	public AdminDiscountDto(Discount discount) {
		this.id = discount.getId();
		this.title = discount.getTitle();
		this.code = discount.getCode();
		this.startDate = discount.getStartDate();
		this.finalDate = discount.getFinalDate();
		this.expired = discount.getExpired();
		this.use = discount.getUse();
		this.orders = discount.getOrders();
	}
	
	

}
