package TFG_Ezyshop_Backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.entities.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{

//	public List<OrderProduct> getAll();
//	public Optional<OrderProduct> getOrderProduct(Long orderProductId);
//	public OrderProduct create(OrderProduct orderProduct); 
//	public void delete(Long orderProductId);
}
