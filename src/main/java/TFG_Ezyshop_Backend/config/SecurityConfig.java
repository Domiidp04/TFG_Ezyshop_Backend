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
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable() // CSRF disable
				.cors().and() // Anadimos el Cors
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeHttpRequests() // Para las perticiones HTTP
				// Ruta auth
				.requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
//				.requestMatchers(HttpMethod.POST, "/payment").permitAll()

				// Todas las rutas de USER
				.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN").requestMatchers(HttpMethod.POST, "/users")
				.hasRole("ADMIN").requestMatchers(HttpMethod.GET, "/users/*").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.PUT, "/users/*").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.DELETE, "/users/*").hasAnyRole("ADMIN", "USER")

				// Rutas para products
				.requestMatchers(HttpMethod.GET, "/products").permitAll().requestMatchers(HttpMethod.GET, "/products/*")
				.permitAll().requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/products/*").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/products/*").hasRole("ADMIN")

				// Rutas para Category
				.requestMatchers(HttpMethod.GET, "/categories").permitAll()
				.requestMatchers(HttpMethod.GET, "/categories/*").permitAll()
				.requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/categories/*").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/categories/*").hasRole("ADMIN")

				// Rutas para Order
				.requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN").requestMatchers(HttpMethod.POST, "/orders")
				.hasRole("USER").requestMatchers(HttpMethod.GET, "/orders/*").hasAnyRole("ADMIN", "USER")

				// Rutas para Assessment
				.requestMatchers(HttpMethod.GET, "/assessments").permitAll()
				.requestMatchers(HttpMethod.GET, "/assessments/*").permitAll()
				.requestMatchers(HttpMethod.POST, "/assessments").hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/assessments/*").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.DELETE, "/assessments/*").hasAnyRole("ADMIN", "USER")

				// Rutas para Discount
				.requestMatchers(HttpMethod.GET, "/discounts").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/discounts/*").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/discounts").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/discounts/*").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/discounts/*").hasRole("ADMIN")
				
				// Rutas para pago
				.requestMatchers(HttpMethod.POST, "/payment/*").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/pay/success/*").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/pay/success").hasRole("USER")

				.anyRequest() // Para cualquiera peticion
				.authenticated() // Necesitamos estar autenticados
				.and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuracion) throws Exception {
		return configuracion.getAuthenticationManager();
	}

}
