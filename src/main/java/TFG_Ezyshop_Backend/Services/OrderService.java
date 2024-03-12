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
		return orderRepository.findAll();
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
