package TFG_Ezyshop_Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import TFG_Ezyshop_Backend.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUserId(Long userId);
	
	@Modifying
	@Query("UPDATE Order o SET o.payment = :paymentStatus WHERE o.id = :orderId")
	void updatePaymentStatus(@Param("orderId") Long orderId, @Param("paymentStatus") boolean paymentStatus);


	
//	public List<Order> getAll();
//	public Optional<Order> getOrder(Long orderId);
//	public Order create(Order order); 
//	public void delete(Long orderId);

}
