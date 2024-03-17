package TFG_Ezyshop_Backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.dto.OrderProductDto;
import TFG_Ezyshop_Backend.services.OrderProductService;


@RestController
@RequestMapping("/ordersProducts")
public class OrderProductController {

	private final OrderProductService orderProductService;
	
	public OrderProductController(OrderProductService orderProductService) {
		this.orderProductService = orderProductService;
	}
	
	@GetMapping
	public ResponseEntity<List<OrderProductDto>> getAll(){
		return new ResponseEntity<List<OrderProductDto>>(orderProductService.getAll(), HttpStatus.OK);
	}
}
