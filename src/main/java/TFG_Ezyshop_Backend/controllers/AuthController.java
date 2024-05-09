package TFG_Ezyshop_Backend.controllers;


import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.config.JwtUtils;
import TFG_Ezyshop_Backend.config.SecurityConfig;
import TFG_Ezyshop_Backend.dto.LoginDto;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.exceptions.EmailAlreadyExistsException;
import TFG_Ezyshop_Backend.exceptions.UsernameAlreadyExistsException;
import TFG_Ezyshop_Backend.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	
	private final JwtUtils jwtUtils;
	
	private final UserService userService;
	
	
	public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService, SecurityConfig securityConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userService = userService;
	}

	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginDto loginDto){
		System.out.println(loginDto.getUsername());
		System.out.println(loginDto.getPassword());
	    UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
	    Authentication authentication = this.authenticationManager.authenticate(login);
	    
	    if (authentication.isAuthenticated()) {
	        // Obtiene el usuario autenticado
	        Optional<UserEntity> optionalUser = userService.getByUsername(loginDto.getUsername());
	        if (!optionalUser.isPresent() || optionalUser.get().getLocked() || optionalUser.get().getDisabled()) {
	            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	        }

	        UserEntity user = optionalUser.get();

	        String jwt = this.jwtUtils.create(loginDto.getUsername());
	        // Obtén el rol del usuario autenticado
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        String userRole = authorities.stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.joining(", "));

	        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
	    } else {
	        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	    }
	}

	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserEntity userEntity) {
		try {
            UserEntity registeredUser = userService.save(userEntity);
            System.out.println(registeredUser.toString());
            return new ResponseEntity<>("Usuario registrado con éxito", HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EmailAlreadyExistsException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
		}


	
}
