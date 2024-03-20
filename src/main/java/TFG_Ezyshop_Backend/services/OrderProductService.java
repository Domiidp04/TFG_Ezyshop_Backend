package TFG_Ezyshop_Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.OrderProductDto;
import TFG_Ezyshop_Backend.entities.OrderProduct;
import TFG_Ezyshop_Backend.repositories.OrderProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderProductService {

	private final OrderProductRepository orderProductRepository;

	public OrderProductService(OrderProductRepository orderProductRepository) {
		this.orderProductRepository = orderProductRepository;
	}
	
	public List<OrderProductDto> getAll(){
		List<OrderProduct>ordersProducts = orderProductRepository.findAll();
		return ordersProducts.stream()
				.map(OrderProductDto::new)
				.collect(Collectors.toList());
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
