package TFG_Ezyshop_Backend.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.config.SecurityConfig;
import TFG_Ezyshop_Backend.dto.AdminUserDto;
import TFG_Ezyshop_Backend.dto.UserDto;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.exceptions.EmailAlreadyExistsException;
import TFG_Ezyshop_Backend.exceptions.UserNotFoundException;
import TFG_Ezyshop_Backend.exceptions.UsernameAlreadyExistsException;
import TFG_Ezyshop_Backend.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	private final SecurityConfig securityConfig;
	
	
	public UserService(UserRepository userRepository, SecurityConfig securityConfig) {
		this.userRepository = userRepository;
		this.securityConfig = securityConfig;
	}

	//Metodo obtencion del rol, para no repetir
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication.getAuthorities();
	}
	
	//Metodo para obtener a un usuario por usename.
	public Optional<UserEntity> getByUsername(String username){
		return userRepository.getByUsername(username);
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




	
	public UserEntity save(UserEntity userEntity) {
	    // Comprueba si el nombre de usuario ya existe
	    if (userRepository.existsByUsername(userEntity.getUsername())) {
	        throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");	    
	    }

	    if (userRepository.existsByEmail(userEntity.getEmail())) {
	        throw new EmailAlreadyExistsException("El email de usuario ya existe");	    
	    }

	    // Obtiene el nombre de usuario del usuario autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = getAuthorities();
	    String authenticatedUsername = authentication.getName();
	    
	    System.out.println("Rol es " + authentication);

	    // Si el usuario autenticado es un administrador, no establece los valores predeterminados
	    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        userEntity.setPassword(this.securityConfig.passwordEncoder().encode(userEntity.getPassword()));
	        userEntity.setDate(new Date());
	    } else {
	        // Valores predeterminados
	        userEntity.setPassword(this.securityConfig.passwordEncoder().encode(userEntity.getPassword()));
	        userEntity.setLocked(false);
	        userEntity.setDisabled(false);
	        userEntity.setRoleId((long) 2);
	        userEntity.setDate(new Date());
	    }

	    return userRepository.save(userEntity);
	}
	
	public UserEntity update(Long id, UserEntity updatedUser) {
	    // Obtiene el nombre de usuario del usuario autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = getAuthorities();
	    String authenticatedUsername = authentication.getName();

	    // Obtiene el usuario que se está intentando actualizar
	    UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

	    // Comprueba si el nombre de usuario ya existe y si es diferente del nombre de usuario actual
//	    if (!user.getUsername().equals(updatedUser.getUsername()) && userRepository.existsByUsername(updatedUser.getUsername())) {
//	        throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");	    
//	    }
//
//	    // Comprueba si el email ya existe y si es diferente del email actual
//	    if (!user.getEmail().equals(updatedUser.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
//	        throw new EmailAlreadyExistsException("El email de usuario ya existe");	    
//	    }

	    // Comprueba si el usuario autenticado es el mismo que el usuario que se está intentando actualizar o si es un administrador
	    if (!user.getUsername().equals(authenticatedUsername) && !authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        throw new AccessDeniedException("No tienes autorización para editar la información de otros usuarios.");
	    }

	    // Si el usuario autenticado es un administrador, puede actualizar todos los campos
	    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) { 
	        updatedUser.setPassword(user.getPassword());
	        System.out.println("ROLES : " + getUser(id));
	        System.out.println("ADMN : " + updatedUser);
	        return userRepository.save(updatedUser);
	    } else {
	        // Si el usuario autenticado no es un administrador, solo puede actualizar ciertos campos
	    	updatedUser.setPassword(user.getPassword());
	    	updatedUser.setLocked(false);
	    	updatedUser.setDisabled(false);
	    	updatedUser.setRoleId((long) 2);
	    	System.out.println("USER : " + updatedUser);
	    	return userRepository.save(updatedUser);
	    }

	}



	
	public Boolean delete(Long userId) {
	    // Obtiene el nombre de usuario del usuario autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = getAuthorities();
	    String authenticatedUsername = authentication.getName();

	    // Obtiene el usuario que se está intentando eliminar
	    Optional<UserEntity> optionalUser = userRepository.findById(userId);
	    if (!optionalUser.isPresent()) {
	        throw new UserNotFoundException("Usuario no encontrado");
	    }
	    UserEntity user = optionalUser.get();

	    // Comprueba si el usuario autenticado es el mismo que el usuario que se está intentando eliminar o si es un administrador
	    if (!user.getUsername().equals(authenticatedUsername) && !authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        throw new AccessDeniedException("No tienes autorización para eliminar la información de otros usuarios.");
	    }

	    // Marca los atributos disabled y locked como true
	    user.setDisabled(true);
	    user.setLocked(true);

	    // Guarda el usuario actualizado en la base de datos
	    userRepository.save(user);

	    return true;
	}

	
	
	
	
}
