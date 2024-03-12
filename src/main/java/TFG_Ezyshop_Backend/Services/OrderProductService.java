package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Entity.OrderProduct;
import TFG_Ezyshop_Backend.Repository.OrderProductRepository;

@Service
public class OrderProductService {

	private final OrderProductRepository orderProductRepository;

	public OrderProductService(OrderProductRepository orderProductRepository) {
		this.orderProductRepository = orderProductRepository;
	}
	
	public List<OrderProduct> getAll(){
		return orderProductRepository.findAll();
	}
	
	public OrderProduct save(OrderProduct orderProduct) {
		return orderProductRepository.save(orderProduct);
	}
	
	public Boolean delete(Long orderProductId) {
		return getOrderProduct(orderProductId).map(orderProduct -> {
			orderProductRepository.deleteById(orderProductId);
		return true;
		}).orElse(false);
	}
	
	public Optional<OrderProduct> getOrderProduct(Long orderProductId){
		return orderProductRepository.findById(orderProductId);
	}
	
	
}
