package TFG_Ezyshop_Backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.dto.OrderRequestDto;
import TFG_Ezyshop_Backend.entities.Order;
import TFG_Ezyshop_Backend.exceptions.StockException;
import TFG_Ezyshop_Backend.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
    public ResponseEntity<?> getAll() {
        List<?> users = orderService.getAll();
        return ResponseEntity.ok(users);
    }
	
	
	 @GetMapping("/{orderId}")
	 public ResponseEntity<?> getById(@PathVariable("orderId") long orderId) {
	     Optional<?> order = orderService.getOrderById(orderId);

	     if (order.isPresent()) {
	         return new ResponseEntity<>(order.get(), HttpStatus.OK);
	     } else {
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }
	 }
	 
	 
	 @PostMapping
	 public ResponseEntity<Order> createOrderWithOrderProducts(@RequestBody OrderRequestDto orderRequestDto) {
	     try {
	         Order order = orderService.createOrderWithOrderProducts(orderRequestDto);
	         return new ResponseEntity<>(order, HttpStatus.CREATED);
	     }catch(StockException s){
	    	 return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	     } catch (Exception e) {
//	         // Registra la excepción para obtener más detalles sobre el error
//	         System.out.println("Se produjo un error: " + e.getMessage());
//	         e.printStackTrace();
	         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }


}
