package TFG_Ezyshop_Backend.dto;

import java.util.Date;

import TFG_Ezyshop_Backend.entities.Discount;
import lombok.Data;

@Data
public class DiscountDto {

	private String title;
	
	private Date startDate;
	
	private Date finalDate;
	
	public DiscountDto(Discount discount) {
		this.title = discount.getTitle();
		this.startDate = discount.getStartDate();
		this.finalDate = discount.getFinalDate();
	}
}
