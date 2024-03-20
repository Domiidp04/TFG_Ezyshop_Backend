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

import TFG_Ezyshop_Backend.UsernameNotFoundException;
import TFG_Ezyshop_Backend.config.SecurityConfig;
import TFG_Ezyshop_Backend.dto.AdminOrderDto;
import TFG_Ezyshop_Backend.dto.OrderDto;
import TFG_Ezyshop_Backend.entities.Order;
import TFG_Ezyshop_Backend.entities.OrderProduct;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final SecurityConfig securityConfig;
	
	private final UserService userService;
	
	public OrderService(OrderRepository orderRepository, SecurityConfig securityConfig, UserService userService) {
		this.orderRepository = orderRepository;
		this.securityConfig = securityConfig;
		this.userService = userService;
	}
	
	//Metodo obtencion del rol, para no repetir
		public Collection<? extends GrantedAuthority> getAuthorities() {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    return authentication.getAuthorities();
		}
	
	public List<?> getAll() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(AdminOrderDto::new)
                .collect(Collectors.toList());
    }

    public Optional<?> getOrderById(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = getAuthorities();
        String authenticatedUsername = authentication.getName();

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            // Comprueba si el usuario autenticado es el mismo que el usuario que se está intentando obtener
            if (!order.get().getUserOrder().getUsername().equals(authenticatedUsername) && !authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new AccessDeniedException("No tienes autorización para acceder a la información de otros usuarios.");
            }

            if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return Optional.of(new AdminOrderDto(order.get()));
            } else {
                return Optional.of(new OrderDto(order.get()));
            }
        } else {
            return Optional.empty();
        }
    }
    
    public Order save(OrderDto orderDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        // Aquí asumimos que tienes un método para obtener el usuario actual
        Optional<UserEntity> optionalUser = userService.getByUsername(authenticatedUsername);

        if (!optionalUser.isPresent()) {
            // Manejar el caso en que 'getByUsername' no devuelve un usuario
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity user = optionalUser.get();

        Order order = new Order();
        order.setUserOrder(user);
        order.setOrderDate(null); // La fecha del pedido se establece en null por defecto

        // Aquí asumimos que tienes un método para calcular el totalAmount
        double totalAmount = calculateTotalAmount(orderDto.getOrderProducts());
        order.setTotalAmount(totalAmount);

        double shippingAmount = 1.99; // El shippingAmount siempre es 1.99
        order.setShippingAmount(shippingAmount);

        // double savedAmount = orderDto.getDiscount() != null ? orderDto.getDiscount() : 0;
        // order.setSavedAmount(savedAmount);

        double paymentAmount = totalAmount + shippingAmount;
        order.setPaymentAmount(paymentAmount);

        return orderRepository.save(order);
    }

    
    public double calculateTotalAmount(List<OrderProduct> list) {
        double totalAmount = 0.0;
        for (OrderProduct orderProduct : list) {
            totalAmount += orderProduct.getProductOrderProduct().getPrice() * orderProduct.getAmount();
        }
        return totalAmount;
    }



	
//	public Order save(Order order) {
//		return orderRepository.save(order);
//	}
	
//	public Boolean delete(Long orderId) {
//		return getOrder(orderId).map(order -> {
//			orderRepository.deleteById(orderId);
//		return true;
//		}).orElse(false);
//	}
	
//	public Optional<Order> getOrder(Long orderId){
//		return orderRepository.findById(orderId);
//	}
}
