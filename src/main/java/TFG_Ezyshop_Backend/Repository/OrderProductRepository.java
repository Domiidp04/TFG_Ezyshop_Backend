package TFG_Ezyshop_Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.Entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{

	public List<OrderProduct> getAll();
	public Optional<OrderProduct> getUser(Long orderProductId);
	public OrderProduct create(OrderProduct orderProduct); 
	public void delete(Long orderProductId);
}
