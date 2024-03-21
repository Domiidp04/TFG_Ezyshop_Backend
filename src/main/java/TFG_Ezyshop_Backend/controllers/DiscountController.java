package TFG_Ezyshop_Backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("/{id}")
    public ResponseEntity<AdminDiscountDto> getDiscountById(@PathVariable Long id) {
        try {
            AdminDiscountDto discount = discountService.getById(id);
            return new ResponseEntity<>(discount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping
    public ResponseEntity<AdminDiscountDto> createDiscount(@RequestBody AdminDiscountDto adminDiscountDto) {
        try {
            AdminDiscountDto discount = discountService.createDiscount(adminDiscountDto);
            return new ResponseEntity<>(discount, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<AdminDiscountDto> updateDiscount(@PathVariable Long id, @RequestBody AdminDiscountDto adminDiscountDto) {
        try {
            AdminDiscountDto discount = discountService.updateDiscount(id, adminDiscountDto);
            return new ResponseEntity<>(discount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
