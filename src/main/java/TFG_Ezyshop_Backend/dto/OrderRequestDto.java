package TFG_Ezyshop_Backend.dto;

import java.util.List;

import TFG_Ezyshop_Backend.entities.OrderProduct;
import lombok.Data;

@Data
public class OrderRequestDto {

	private List<OrderProduct>orderProducts;
	
	private String discountCode;
	
}
