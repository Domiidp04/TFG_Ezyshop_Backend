package TFG_Ezyshop_Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.OrderDto;
import TFG_Ezyshop_Backend.entities.Order;
import TFG_Ezyshop_Backend.repositories.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public List<OrderDto> getAll(){
		List<Order> orders = orderRepository.findAll();
		return orders.stream()
				.map(OrderDto::new)
				.collect(Collectors.toList());
	}
	
	public Order save(Order order) {
		return orderRepository.save(order);
	}
	
	public Boolean delete(Long orderId) {
		return getOrder(orderId).map(order -> {
			orderRepository.deleteById(orderId);
		return true;
		}).orElse(false);
	}
	
	public Optional<Order> getOrder(Long orderId){
		return orderRepository.findById(orderId);
	}
}
