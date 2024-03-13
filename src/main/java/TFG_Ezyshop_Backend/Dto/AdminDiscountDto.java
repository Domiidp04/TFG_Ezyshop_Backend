package TFG_Ezyshop_Backend.Dto;

import java.util.Date;

import TFG_Ezyshop_Backend.Entity.Discount;
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
	
	public AdminDiscountDto(Discount discount) {
		this.id = discount.getId();
		this.title = discount.getTitle();
		this.code = discount.getCode();
		this.startDate = discount.getStartDate();
		this.finalDate = discount.getFinalDate();
		this.expired = discount.getExpired();
		this.use = discount.getUse();
	}
}
