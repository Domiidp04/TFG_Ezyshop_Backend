package TFG_Ezyshop_Backend.dto;

import java.util.Date;

import TFG_Ezyshop_Backend.entities.Discount;
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
	
	private Double amount;
	
	 public AdminDiscountDto() {
	        // Constructor vacío necesario para la deserialización de JSON
	    }

	public AdminDiscountDto(Discount discount) {
		this.id = discount.getId();
		this.title = discount.getTitle();
		this.code = discount.getCode();
		this.startDate = discount.getStartDate();
		this.finalDate = discount.getFinalDate();
		this.expired = discount.getExpired();
		this.use = discount.getUse();
		this.amount = discount.getAmount();
	}
	
	

}
