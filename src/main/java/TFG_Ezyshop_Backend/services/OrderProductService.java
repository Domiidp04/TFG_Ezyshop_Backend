package TFG_Ezyshop_Backend.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.dto.AdminOrderProductDto;
import TFG_Ezyshop_Backend.dto.OrderProductDto;
import TFG_Ezyshop_Backend.entities.Order;
import TFG_Ezyshop_Backend.entities.OrderProduct;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.exceptions.UsernameNotFoundException;
import TFG_Ezyshop_Backend.repositories.OrderProductRepository;
import TFG_Ezyshop_Backend.repositories.OrderRepository;
import TFG_Ezyshop_Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderProductService {

	private final OrderProductRepository orderProductRepository;
	
	private final UserRepository userRepository;
	
	private final OrderRepository orderRepository;

	public OrderProductService(OrderProductRepository orderProductRepository, UserRepository userRepository, OrderRepository orderRepository) {
		this.orderProductRepository = orderProductRepository;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
	}
	
	public List<?> getAll() {
	    // Obtén el nombre del usuario autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String authenticatedUsername = authentication.getName();
	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	    // Busca al usuario en la base de datos
	    Optional<UserEntity> optionalUser = userRepository.getByUsername(authenticatedUsername);

	    // Si el usuario no existe, lanza una excepción
	    if (!optionalUser.isPresent()) {
	        throw new UsernameNotFoundException("User not found");
	    }

	    UserEntity user = optionalUser.get();

	    // Comprueba el rol del usuario
	    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        // Si el usuario es un administrador, devuelve todos los OrderProductDto
	        List<?> allOrderProductDtos = orderProductRepository.findAll()
	            .stream()
	            .map(orderProduct -> new AdminOrderProductDto(orderProduct)) // Usa un método para convertir OrderProduct a OrderProductDto
	            .collect(Collectors.toList());
	        System.out.println("AADMIN");

	        return allOrderProductDtos;
	    } else {
	        // Si el usuario no es un administrador, devuelve solo sus OrderProductDto
	        List<Order> userOrders = orderRepository.findByUserId(user.getId());
	        List<OrderProductDto> userOrderProductDtos = new ArrayList<>();
	        for (Order order : userOrders) {
	            List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(order.getId());
	            List<OrderProductDto> orderProductDtos = orderProducts.stream()
	                .map(orderProduct -> new OrderProductDto(orderProduct)) // Usa un método para convertir OrderProduct a OrderProductDto
	                .collect(Collectors.toList());
	            userOrderProductDtos.addAll(orderProductDtos);
	        }
	        System.out.println("USER");
	        return userOrderProductDtos;
	    }
	}

	public Optional<?> getById(Long id) {
	    // Obtén el nombre del usuario autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String authenticatedUsername = authentication.getName();
	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	    // Busca al usuario en la base de datos
	    Optional<UserEntity> optionalUser = userRepository.getByUsername(authenticatedUsername);

	    // Si el usuario no existe, lanza una excepción
	    if (!optionalUser.isPresent()) {
	        throw new UsernameNotFoundException("User not found");
	    }

	    UserEntity user = optionalUser.get();

	    // Busca el OrderProduct en la base de datos
	    Optional<OrderProduct> optionalOrderProduct = orderProductRepository.findById(id);

	    // Si el OrderProduct no existe, devuelve un Optional vacío
	    if (!optionalOrderProduct.isPresent()) {
	        return Optional.empty();
	    }

	    OrderProduct orderProduct = optionalOrderProduct.get();

	    // Comprueba el rol del usuario
	    if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        // Si el usuario es un administrador, devuelve un AdminOrderProductDto
	        return Optional.of(new AdminOrderProductDto(orderProduct));
	    } else {
	        // Si el usuario no es un administrador, comprueba si el OrderProduct pertenece al usuario
	        List<Order> userOrders = orderRepository.findByUserId(user.getId());
	        for (Order order : userOrders) {
	            List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(order.getId());
	            if (orderProducts.contains(orderProduct)) {
	                // Si el OrderProduct pertenece al usuario, devuelve un OrderProductDto
	                return Optional.of(new OrderProductDto(orderProduct));
	            }
	        }
	        // Si el OrderProduct no pertenece al usuario, devuelve un Optional vacío
	        return Optional.empty();
	    }
	}




	
	
	
	
}
