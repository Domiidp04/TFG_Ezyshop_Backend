package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Dto.ProductDto;
import TFG_Ezyshop_Backend.Entity.Product;
import TFG_Ezyshop_Backend.Repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	/*
	 * Admin: Verlos todos con AdminProductDto.
	 * User: Ver los que estan activos.
	 * Si un producto tiene stock 0, automaticamente se desactiva
	 * */
	public List<ProductDto> getAll(){
		List<Product>products = productRepository.findAll();
		return products.stream()
				.map(ProductDto::new)
				.collect(Collectors.toList());
	}
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public Boolean delete(Long productId) {
		return getProduct(productId).map(product -> {
			productRepository.deleteById(productId);
			return true;
		}).orElse(false);
	}
	
	public Optional<Product> getProduct(Long productId){
		return productRepository.findById(productId);
	}

}
