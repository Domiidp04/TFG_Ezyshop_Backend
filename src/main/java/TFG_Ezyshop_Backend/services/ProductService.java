package TFG_Ezyshop_Backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.ProductDto;
import TFG_Ezyshop_Backend.entities.Product;
import TFG_Ezyshop_Backend.repositories.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<ProductDto> getAll(){
		List<Product> products = productRepository.findAll();
		return products.stream()
				.map(ProductDto::new)
				.collect(Collectors.toList());
	}
	
	
}
