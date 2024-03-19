package TFG_Ezyshop_Backend.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminUserDto;
import TFG_Ezyshop_Backend.dto.UserDto;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	//Metodo obtencion del rol, para no repetir
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication.getAuthorities();
	}


	public List<?> getAll() {
	    List<UserEntity> users = userRepository.findAll();

	        return users.stream()
	                .map(AdminUserDto::new)
	                .collect(Collectors.toList());
	    
	}

	
	public Optional<?> getUser(Long userId) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = getAuthorities();
	    String authenticatedUsername = authentication.getName();

	    Optional<UserEntity> user = userRepository.findById(userId);

	    if (user.isPresent()) {
	        // Comprueba si el usuario autenticado es el mismo que el usuario que se está intentando obtener
	        if (!user.get().getUsername().equals(authenticatedUsername) && !authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	            throw new AccessDeniedException("No tienes autorización para acceder a la información de otros usuarios.");
	        }

	        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	            return Optional.of(new AdminUserDto(user.get()));
	        } else {
	            return Optional.of(new UserDto(user.get()));
	        }
	    } else {
	        return Optional.empty();
	    }
	}




	
	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}
	
	public Boolean delete(Long userId) {
		return getUser(userId).map(user -> {
			userRepository.deleteById(userId);
		return true;
		}).orElse(false);
	}
	
	
}
