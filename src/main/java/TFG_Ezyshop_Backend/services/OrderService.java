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

import TFG_Ezyshop_Backend.dto.AdminOrderDto;
import TFG_Ezyshop_Backend.dto.OrderDto;
import TFG_Ezyshop_Backend.dto.OrderRequestDto;
import TFG_Ezyshop_Backend.entities.Discount;
import TFG_Ezyshop_Backend.entities.Order;
import TFG_Ezyshop_Backend.entities.OrderProduct;
import TFG_Ezyshop_Backend.entities.Product;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.exceptions.StockException;
import TFG_Ezyshop_Backend.exceptions.UsernameNotFoundException;
import TFG_Ezyshop_Backend.repositories.DiscountRepository;
import TFG_Ezyshop_Backend.repositories.OrderProductRepository;
import TFG_Ezyshop_Backend.repositories.OrderRepository;
import TFG_Ezyshop_Backend.repositories.ProductRepository;
import TFG_Ezyshop_Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final UserRepository userRepository;
	
	private final ProductRepository productRepository;
	
	private final OrderProductRepository orderProductRepository;
	
	private final DiscountRepository discountRepository;
	
	
	public OrderService(OrderRepository orderRepository, 
						UserRepository userRepository, 
						ProductRepository productRepository, 
						OrderProductRepository orderProductRepository, 
						DiscountRepository discountRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderProductRepository = orderProductRepository;
		this.discountRepository = discountRepository;
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
    
    @Transactional
    public Order createOrderWithOrderProducts(OrderRequestDto orderRequestDto) throws Exception {
        
    	// Obtén la lista de productos del pedido y el código de descuento del DTO
        List<OrderProduct> orderProducts = orderRequestDto.getOrderProducts();
        String discountCode = orderRequestDto.getDiscountCode();
    	
    	// Obtén el nombre del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        // Busca al usuario en la base de datos
        Optional<UserEntity> optionalUser = userRepository.getByUsername(authenticatedUsername);

        // Si el usuario no existe, lanza una excepción
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity user = optionalUser.get();

        // Crear un nuevo pedido
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderDate(new Date());
        order.setShippingAmount(1.99);
        order.setDiscountOrder(null);
        order.setSavedAmount(null);
        order.setTotalAmount(0.0);
        order.setPaymentAmount(0.0);
        order.setPayment(false);
        order.setPaymentId(null);
        
        

        // Guardar el pedido en la base de datos
        order = orderRepository.save(order);

        double totalAmount = 0;

        // Para cada producto del pedido en la lista
        for (OrderProduct orderProduct : orderProducts) {
            // Buscar el producto en la base de datos
            Optional<Product> optionalProduct = productRepository.findById(orderProduct.getProductId());
            if (!optionalProduct.isPresent()) {
                throw new Exception("Product not found");
            }
            Product product = optionalProduct.get();

            // Asignar el pedido y el producto al producto del pedido
            orderProduct.setOrderId(order.getId());
            orderProduct.setProductId(product.getId());
            
         // Si el discountPrice no es null, usarlo. Si no, usar el price normal.
            double price = (product.getDiscountPrice() != null) ? product.getDiscountPrice() : product.getPrice();
            orderProduct.setPrice(price);
            
         // Actualizar el stock del producto
            int newStock = product.getStock() - orderProduct.getAmount();
            if (newStock < 0) {
                throw new StockException("Insufficient stock for product " + product.getId());
            }
            product.setStock(newStock);
            productRepository.save(product);
            productRepository.flush();

            // Guardar el producto del pedido en la base de datos
            orderProduct = orderProductRepository.save(orderProduct);

            // Sumar el precio del producto del pedido al total del pedido
            totalAmount += orderProduct.getPrice() * orderProduct.getAmount();
        }

     // Establecer el total del pedido
        order.setTotalAmount(totalAmount + order.getShippingAmount());
        double paymentAmount = order.getTotalAmount();

     // Si se proporcionó un código de descuento, intenta aplicarlo
        if (discountCode != null && !discountCode.isEmpty()) {
            Optional<Discount> optionalDiscount = discountRepository.findByCode(discountCode);
            discountRepository.flush();
            
            
            if (optionalDiscount.isPresent()) {
                Discount discount = optionalDiscount.get();
                // Comprobar si el descuento es válido, está habilitado y no está expirado
                if (!discount.getExpired() && discount.getUse() > 0 && (discount.getStartDate().before(new Date()) && (discount.getFinalDate() == null || discount.getFinalDate().after(new Date())))) {                    // Aplicar el descuento
                    double discountAmount = discount.getAmount();
                    order.setSavedAmount(discountAmount);
                    order.setDiscountId(discount.getId());
                    paymentAmount -= discountAmount;

                    // Actualizar los usos del descuento
                    discount.setUse(discount.getUse() - 1);
                    discountRepository.save(discount);
                } else {
                    // El descuento no es válido, está deshabilitado o está expirado, establecer el descuento y la cantidad guardada en null
                    order.setDiscountId(null);
                    order.setSavedAmount(null);
                }
            } else {
                // El descuento no existe, establecer el descuento y la cantidad guardada en null
                order.setDiscountId(null);
                order.setSavedAmount(null);
            }
        } else {
            // No se proporcionó un código de descuento, establecer el descuento y la cantidad guardada en null
            order.setDiscountId(null);
            order.setSavedAmount(null);
        }

        // Establecer la cantidad de pago
        order.setPaymentAmount(paymentAmount);

        // Guardar el pedido en la base de datos
        order = orderRepository.save(order);

        return order;


    }
    
    @Transactional
    public Order updatePaymentStatus(Long orderId, boolean paymentStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (!optionalOrder.isPresent()) {
            // Lanza una excepción o maneja el caso en que el pedido no se encuentra
            throw new RuntimeException("Order not found with id: " + orderId);
        }

        Order order = optionalOrder.get();
        order.setPayment(paymentStatus);
        System.out.println(order.getPayment());
        // Guarda el pedido actualizado en la base de datos
        orderRepository.save(order);
        orderRepository.flush();
        return order;
        
    }


	

}
