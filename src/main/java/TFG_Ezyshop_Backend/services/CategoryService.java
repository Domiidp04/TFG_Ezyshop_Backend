package TFG_Ezyshop_Backend.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminCategoryDto;
import TFG_Ezyshop_Backend.dto.CategoryDto;
import TFG_Ezyshop_Backend.entities.Category;
import TFG_Ezyshop_Backend.repositories.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
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
	        List<Category> categories = categoryRepository.findAll();
	        String role = authorities.stream()
	                                .map(GrantedAuthority::getAuthority)
	                                .findFirst()
	                                .orElse("USER"); // Default to "USER" if no role is found
	        if(role.equals("ROLE_ADMIN")) {
	            return categories.stream()
	                    .map(AdminCategoryDto::new)
	                    .collect(Collectors.toList());
	        } else {
	            return categories.stream()
	                    .filter(category -> !category.getDisabled()) // Filtrar categorías deshabilitadas
	                    .map(CategoryDto::new)
	                    .collect(Collectors.toList());
	        }
	    }

	    public Optional<?> getById(Long categoryId) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        Collection<? extends GrantedAuthority> authorities = getAuthorities();
	        String authenticatedUsername = authentication.getName();
	        Optional<Category> category = categoryRepository.findById(categoryId);

	        if (category.isPresent()) {
	            String role = authorities.stream()
	                                    .map(GrantedAuthority::getAuthority)
	                                    .findFirst()
	                                    .orElse("USER"); // Default to "USER" if no role is found
	            if(role.equals("ROLE_ADMIN")) {
	                return Optional.of(new AdminCategoryDto(category.get()));
	            } else {
	                if(category.get().getDisabled()) {
	                    return Optional.empty();
	                }
	                return Optional.of(new CategoryDto(category.get()));
	            }
	        } else {
	            return Optional.empty();
	        }
	    }

	    public Category save(Category category) {
	        if (category.getProducts() == null || category.getProducts().isEmpty()) {
	            category.setDisabled(true);
	        }
	        return categoryRepository.save(category);
	    }


	    public Optional<Category> update(Long id, Category category) {
	        if (!id.equals(category.getId())) {
	            return Optional.empty(); // Retorna un Optional vacío si los IDs no coinciden
	        }

	        return categoryRepository.findById(id)
	                .map(categoryUpdate -> categoryRepository.save(category));
	    }

	    public Boolean delete(Long id) {
	        return categoryRepository.findById(id)
	                .map(category -> {
	                    category.setDisabled(true);
	                    categoryRepository.save(category);
	                    return true; // Devuelve true si la categoría se encuentra y se actualiza
	                })
	                .orElse(false); // Devuelve false si la categoría no se encuentra
	    }
	}

	
