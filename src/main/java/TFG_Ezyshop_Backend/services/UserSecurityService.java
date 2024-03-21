package TFG_Ezyshop_Backend.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserSecurityService implements UserDetailsService{

	private final UserRepository userRepository;	
		
	public UserSecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    // Obtener el usuario de la base de datos
	    UserEntity user = this.userRepository.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no se ha encontrado."));
	    
	    // Obtener el rol del usuario
	    String role = user.getRoleUser().getRole();
	    
	    return User.builder()
	            .username(user.getUsername())
	            .password(user.getPassword())
	            .roles(new String[]{role})  // Aquí pasamos el rol como un array de un solo elemento
	            .disabled(user.getDisabled())
	            .accountLocked(user.getLocked())
	            .build();
	}

}
