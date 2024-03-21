package TFG_Ezyshop_Backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.dto.AdminDiscountDto;
import TFG_Ezyshop_Backend.services.DiscountService;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

	private final DiscountService discountService;

	public DiscountController(DiscountService discountService) {
		this.discountService = discountService;
	}
	
	@GetMapping
	public ResponseEntity<List<AdminDiscountDto>> getAll(){
		return new ResponseEntity<List<AdminDiscountDto>>(discountService.getAll(), HttpStatus.OK);
	}
}
