package TFG_Ezyshop_Backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUserId(Long userId);

	
//	public List<Order> getAll();
//	public Optional<Order> getOrder(Long orderId);
//	public Order create(Order order); 
//	public void delete(Long orderId);

}
