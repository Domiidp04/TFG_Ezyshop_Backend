package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Entity.Order;
import TFG_Ezyshop_Backend.Repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public List<Order> getAll(){
		return orderRepository.getAll();
	}
	
	public Order save(Order order) {
		return orderRepository.create(order);
	}
	
	public Boolean delete(Long orderId) {
		return getOrder(orderId).map(order -> {
			orderRepository.delete(orderId);
		return true;
		}).orElse(false);
	}
	
	public Optional<Order> getOrder(Long orderId){
		return orderRepository.getOrder(orderId);
	}
}
