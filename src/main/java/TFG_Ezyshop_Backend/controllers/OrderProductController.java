package TFG_Ezyshop_Backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.exceptions.UsernameNotFoundException;
import TFG_Ezyshop_Backend.services.OrderProductService;


@RestController
@RequestMapping("/ordersProducts")
public class OrderProductController {

	private final OrderProductService orderProductService;
	
	public OrderProductController(OrderProductService orderProductService) {
		this.orderProductService = orderProductService;
	}
	
    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        try {
            List<?> allOrderProducts = orderProductService.getAll();
            return new ResponseEntity<>(allOrderProducts, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderProductById(@PathVariable Long id) {
        Optional<?> orderProductDto = orderProductService.getById(id);
        return orderProductDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
}
