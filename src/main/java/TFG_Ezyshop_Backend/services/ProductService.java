package TFG_Ezyshop_Backend.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminProductDto;
import TFG_Ezyshop_Backend.dto.ProductDto;
import TFG_Ezyshop_Backend.entities.Product;
import TFG_Ezyshop_Backend.repositories.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	//Metodo obtencion del rol, para no repetir
		public Collection<? extends GrantedAuthority> getAuthorities() {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    return authentication.getAuthorities();
		}
		
		
	
		public List<?> getAll(){
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    Collection<? extends GrantedAuthority> authorities = getAuthorities();
		    String authenticatedUsername = authentication.getName();
		    List<Product> products = productRepository.findAll();
		    String role = authorities.stream()
		                            .map(GrantedAuthority::getAuthority)
		                            .findFirst()
		                            .orElse("USER"); // Default to "USER" if no role is found
		    if(role.equals("ROLE_ADMIN")) {
		        return products.stream()
		                .map(AdminProductDto::new)
		                .collect(Collectors.toList());
		    } else {
		        return products.stream()
		                .filter(product -> !product.getDisabled()) // Filtrar productos deshabilitados
		                .map(ProductDto::new)
		                .collect(Collectors.toList());
		    }
		}
	
		public Optional<?> getById(Long productId) {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    Collection<? extends GrantedAuthority> authorities = getAuthorities();
		    String authenticatedUsername = authentication.getName();
		    Optional<Product> product = productRepository.findById(productId);

		    if (product.isPresent()) {
		        String role = authorities.stream()
		                                .map(GrantedAuthority::getAuthority)
		                                .findFirst()
		                                .orElse("USER"); // Default to "USER" if no role is found
		        if(role.equals("ROLE_ADMIN")) {
		            return Optional.of(new AdminProductDto(product.get()));
		        } else {
		            if(product.get().getDisabled()) {
		                return Optional.empty();
		            }
		            return Optional.of(new ProductDto(product.get()));
		        }
		    } else {
		        return Optional.empty();
		    }
		}
		
		
		
		public Product save(Product product) {
		    return productRepository.save(product);
		}
		

		public Optional<Product> update(Long id, Product product) {
		    if (!id.equals(product.getId())) {
		        return Optional.empty(); // Retorna un Optional vacío si los IDs no coinciden
		    }

		    return productRepository.findById(id)
		            .map(productUpdate -> productRepository.save(product));
		}


		public Boolean delete(Long id) {
		    return productRepository.findById(id)
		            .map(product -> {
		                product.setDisabled(true);
		                productRepository.save(product);
		                return true; // Devuelve true si el producto se encuentra y se actualiza
		            })
		            .orElse(false); // Devuelve false si el producto no se encuentra
		}





	
	
}
