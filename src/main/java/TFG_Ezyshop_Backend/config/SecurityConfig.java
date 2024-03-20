package TFG_Ezyshop_Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@SuppressWarnings(value = "all")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf().disable()		 // CSRF disable
			.cors().and()					 // Anadimos el Cors
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests() //Para las perticiones HTTP
			//Todas las rutas de USER
			.requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
			.requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN")
			.requestMatchers(HttpMethod.GET, "/users/*").hasAnyRole("ADMIN", "USER")
			.requestMatchers(HttpMethod.PUT, "/users/*").hasAnyRole("ADMIN","USER")
			.requestMatchers(HttpMethod.DELETE, "/users/*").hasAnyRole("ADMIN", "USER")
			.requestMatchers(HttpMethod.GET, "/products").permitAll()
			.anyRequest()			// Para cualquiera peticion
			.authenticated()		//Necesitamos estar autenticados
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			;
		return http.build();
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuracion) throws Exception{
		return configuracion.getAuthenticationManager();
	}
	
}
