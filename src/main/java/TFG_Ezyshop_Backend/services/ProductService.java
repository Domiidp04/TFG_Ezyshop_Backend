package TFG_Ezyshop_Backend.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.ProductDto;
import TFG_Ezyshop_Backend.entities.Product;
import TFG_Ezyshop_Backend.repositories.ProductRepository;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
//	@Autowired
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductService.class);
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	/*
	 * Admin: Verlos todos con AdminProductDto.
	 * User: Ver los que estan activos.
	 * Si un producto tiene stock 0, automaticamente se desactiva
	 * */
//	public List<ProductDto> getAll(){
//		System.out.println("Obteniendo");
//		List<Product>products = productRepository.findAll();
//		System.out.println(products);
//		return products.stream()
//				.map(ProductDto::new)
//				.collect(Collectors.toList());
//	}
	
	public List<ProductDto> getAll(){
        logger.info("Inicio del método getAll");
        List<Product> products = null;
        try {
            logger.info("Intentando obtener todos los productos del repositorio");
            products = productRepository.findAll();
            logger.info("Productos obtenidos del repositorio: {}", products);
        } catch (Exception e) {
            logger.error("Error al obtener los productos del repositorio", e);
        }

        if (products == null) {
            logger.warn("La lista de productos es null");
            return Collections.emptyList();
        }

        List<ProductDto> productDtos = null;
        try {
            logger.info("Intentando convertir los productos a ProductDto");
            productDtos = products.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
            logger.info("Productos convertidos a ProductDto: {}", productDtos);
        } catch (Exception e) {
            logger.error("Error al convertir los productos a ProductDto", e);
        }

        logger.info("Fin del método getAll");
        return productDtos;
    }
	
	public Optional<Product> getById(Long id) {
		return productRepository.findById(id);
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
