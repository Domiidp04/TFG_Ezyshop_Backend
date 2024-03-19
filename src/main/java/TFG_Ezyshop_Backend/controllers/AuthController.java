package TFG_Ezyshop_Backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import TFG_Ezyshop_Backend.config.JwtUtils;
import TFG_Ezyshop_Backend.dto.LoginDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(LoginDto loginDto){
		UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
		Authentication authentication = this.authenticationManager.authenticate(login);
		
		if (authentication.isAuthenticated()) {
			String jwt = this.jwtUtils.create(loginDto.getUsername());
			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
		}else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}
	
}
