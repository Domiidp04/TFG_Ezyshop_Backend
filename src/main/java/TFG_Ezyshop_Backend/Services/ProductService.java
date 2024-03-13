package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Dto.AdminProductDto;
import TFG_Ezyshop_Backend.Entity.Product;
import TFG_Ezyshop_Backend.Repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
//	@Autowired
	private  final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	/*
	 * Admin: Verlos todos con AdminProductDto.
	 * User: Ver los que estan activos.
	 * Si un producto tiene stock 0, automaticamente se desactiva
	 * */
	public List<AdminProductDto> getAll(){
		System.out.println("Obteniendo");
		List<Product>products = productRepository.findAll();
		System.out.println(products);
		return products.stream()
				.map(AdminProductDto::new)
				.collect(Collectors.toList());
	}
	
//	public Product save(Product product) {
//		return productRepository.save(product);
//	}
//	
//	public Boolean delete(Long productId) {
//		return getProduct(productId).map(product -> {
//			productRepository.deleteById(productId);
//			return true;
//		}).orElse(false);
//	}
//	
//	public Optional<Product> getProduct(Long productId){
//		return productRepository.findById(productId);
//	}

}
