package TFG_Ezyshop_Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
//	public List<Order> getAll();
//	public Optional<Order> getOrder(Long orderId);
//	public Order create(Order order); 
//	public void delete(Long orderId);

}
