package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Entity.Product;
import TFG_Ezyshop_Backend.Repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> getAll(){
		return productRepository.getAll();
	}
	
	public Product save(Product product) {
		return productRepository.create(product);
	}
	
	public Boolean delete(Long productId) {
		return getProduct(productId).map(product -> {
			productRepository.delete(productId);
			return true;
		}).orElse(false);
	}
	
	public Optional<Product> getProduct(Long productId){
		return productRepository.getProduct(productId);
	}

}
