package TFG_Ezyshop_Backend.controllers;


import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.config.JwtUtils;
import TFG_Ezyshop_Backend.config.SecurityConfig;
import TFG_Ezyshop_Backend.dto.LoginDto;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.repositories.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	
	private final JwtUtils jwtUtils;
	
	private final UserRepository userRepository;
	
	private final SecurityConfig securityConfig;
	
	public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, SecurityConfig securityConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userRepository = userRepository;
		this.securityConfig = securityConfig;
	}

	
	


	@PostMapping("/login")
	public ResponseEntity<Void> login(LoginDto loginDto){
		UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
		Authentication authentication = this.authenticationManager.authenticate(login);
		
		if (authentication.isAuthenticated()) {
			String jwt = this.jwtUtils.create(loginDto.getUsername());
			// Obtén el rol del usuario autenticado
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        String userRole = authorities.stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.joining(", "));

	        // Imprime el rol en la consola
	        System.out.println("El rol del usuario es: " + userRole);
			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
		}else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserEntity userEntity) {
	    // Comprueba si el nombre de usuario ya existe
	    if (userRepository.existsByUsername(userEntity.getUsername())) {
	        return new ResponseEntity<>("El nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
	    }

	    userEntity.setAssessments(null);
	    userEntity.setPassword(this.securityConfig.passwordEncoder().encode(userEntity.getPassword()));
	    // Valores predeterminados
	    userEntity.setLocked(false);
	    userEntity.setDisabled(false);
	    userEntity.setRoleId((long) 1);
	    userEntity.setDate(new Date());

	    // Guarda el nuevo usuario en la base de datos
//	    userRepository.save(userEntity);
	    System.out.println(userEntity.toString());
	    return new ResponseEntity<>("Usuario registrado con éxito", HttpStatus.CREATED);
	}


	
}
