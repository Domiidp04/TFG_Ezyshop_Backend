package TFG_Ezyshop_Backend.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminProductDto;
import TFG_Ezyshop_Backend.dto.ProductDto;
import TFG_Ezyshop_Backend.entities.Category;
import TFG_Ezyshop_Backend.entities.Product;
import TFG_Ezyshop_Backend.repositories.CategoryRepository;
import TFG_Ezyshop_Backend.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	private final CategoryService categoryService;

	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
			CategoryService categoryService) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.categoryService = categoryService;
	}

	// Metodo obtencion del rol, para no repetir
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities();
	}

	public List<?> getAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = getAuthorities();
		String authenticatedUsername = authentication.getName();
		List<Product> products = productRepository.findAll();
		String role = authorities.stream().map(GrantedAuthority::getAuthority).findFirst().orElse("USER"); // Default to
																											// "USER" if
																											// no role
																											// is found
		if (role.equals("ROLE_ADMIN")) {
			return products.stream().map(AdminProductDto::new).collect(Collectors.toList());
		} else {
			return products.stream().filter(product -> !product.getDisabled()) // Filtrar productos deshabilitados
					.map(ProductDto::new).collect(Collectors.toList());
		}
	}

	public Optional<?> getById(Long productId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = getAuthorities();
		String authenticatedUsername = authentication.getName();
		Optional<Product> product = productRepository.findById(productId);

		if (product.isPresent()) {
			String role = authorities.stream().map(GrantedAuthority::getAuthority).findFirst().orElse("USER"); // Default
																												// to
																												// "USER"
																												// if no
																												// role
																												// is
																												// found
			if (role.equals("ROLE_ADMIN")) {
				return Optional.of(new AdminProductDto(product.get()));
			} else {
				if (product.get().getDisabled()) {
					return Optional.empty();
				}
				return Optional.of(new ProductDto(product.get()));
			}
		} else {
			return Optional.empty();
		}
	}

//	@Transactional
//	public List<?> getByProductTitle(String productTitle) {
//	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    Collection<? extends GrantedAuthority> authorities = getAuthorities();
//	    String authenticatedUsername = authentication.getName();
//	    System.out.println("Antes");
//	    List<Product> products = productRepository.findByTitle("%" + productTitle + "%");
//	    
//	    if (products.isEmpty()) {
//
//		    System.out.println("Productos " + products);
//	    	return products;
//	    }
//
//	    return products.stream()
//	        .filter(product -> !product.getDisabled())  // Filtrar productos deshabilitados
//	        .map(product -> {
//	            String role = authorities.stream().map(GrantedAuthority::getAuthority).findFirst().orElse("USER");
//	            if (role.equals("ROLE_ADMIN")) {
//	                return new AdminProductDto(product);
//	            } else {
//	                return new ProductDto(product);
//	            }
//	        }).collect(Collectors.toList());
//	}


	@Transactional
	public Product save(Product product) {
		Category category = product.getCategoryProduct();
		if (category != null) {
			category.getProducts().add(product);
			if (category.getProducts().stream().allMatch(Product::getDisabled)) {
				category.setDisabled(true);
			}
			categoryService.save(category);
		}
		return productRepository.save(product);
	}

	@Transactional
	public Optional<Product> update(Long id, Product product) {
		if (!id.equals(product.getId())) {
			return Optional.empty(); // Retorna un Optional vacío si los IDs no coinciden
		}

		return productRepository.findById(id).map(productUpdate -> {
			// Si el stock del producto es 0, desactiva el producto
			if (product.getStock() == 0) {
				product.setDisabled(true);
			}

			productUpdate = productRepository.save(product);
			// Forzar la transaccion
			productRepository.flush();

			// Si el producto está deshabilitado, verifica si todos los productos en la
			// categoría están deshabilitados
			if (product.getDisabled()) {
				Category category = product.getCategoryProduct();
				if (category != null && category.getProducts().stream().allMatch(Product::getDisabled)) {
					category.setDisabled(true);
					categoryService.save(category);
				}
			}

			return productUpdate;
		});
	}

	public Boolean delete(Long id) {
		return productRepository.findById(id).map(product -> {
			product.setDisabled(true);
			productRepository.save(product);
			return true; // Devuelve true si el producto se encuentra y se actualiza
		}).orElse(false); // Devuelve false si el producto no se encuentra
	}
	
	public List<Product> getProductsBytitle(String title) {
	    List<Product> products = productRepository.findByTitle(title);
	    if (products == null) {
	        System.out.println("La lista de productos es nula.");
	    } else if (products.isEmpty()) {
	        System.out.println("La lista de productos está vacía.");
	    } else {
	        System.out.println("Productos encontrados: " + products.size());
	        System.out.println(products);
	    }
	    return products;
	}


}
