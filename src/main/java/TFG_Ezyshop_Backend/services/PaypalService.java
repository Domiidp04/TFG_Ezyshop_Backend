package TFG_Ezyshop_Backend.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import TFG_Ezyshop_Backend.entities.Order;
import TFG_Ezyshop_Backend.entities.UserEntity;
import TFG_Ezyshop_Backend.exceptions.OrderNotFoundException;
import TFG_Ezyshop_Backend.exceptions.PaymentAlreadyMadeException;
import TFG_Ezyshop_Backend.exceptions.PaymentIdAlreadyUsedException;
import TFG_Ezyshop_Backend.exceptions.UnauthorizedPaymentException;
import TFG_Ezyshop_Backend.exceptions.UsernameNotFoundException;
import TFG_Ezyshop_Backend.repositories.OrderRepository;
import TFG_Ezyshop_Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class PaypalService {

	private final APIContext apiContext;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	private final OrderRepository orderRepository;

	private final UserRepository userRepository;

	public PaypalService(APIContext apiContext, OrderRepository orderRepository, OrderService orderService,
			UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.apiContext = apiContext;
		this.userRepository = userRepository;
	}

	public Payment createPayment(Double total, String currency, String method, String intent, String cancelUrl,
			String successUrl, Long orderId) throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		Locale.setDefault(new Locale("en", "US"));
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		successUrl = successUrl + "?orderId=" + orderId;
		
		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		System.out.println(payment.toString());

		return payment.create(apiContext);
	}

	public Payment createPayment(Long orderId) throws PayPalRESTException, OrderNotFoundException, PaymentAlreadyMadeException, UnauthorizedPaymentException {
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

	    // Buscar el pedido por su ID
	    Optional<Order> optionalOrder = orderRepository.findById(orderId);

	    // Si el pedido no existe, lanza una excepción
	    if (!optionalOrder.isPresent()) {
	        throw new OrderNotFoundException("Order not found with id: " + orderId);
	    }

	    // Si el pedido ya esta pagado, lanza una excepción
	    if (optionalOrder.get().getPayment()) {
	        throw new PaymentAlreadyMadeException("Order paymented: " + orderId);
	    }

	    Order order = optionalOrder.get();

	    // Comprueba si el pedido pertenece al usuario autenticado
	    if (!order.getUserId().equals(user.getId())) {
	        throw new UnauthorizedPaymentException("User not authorized to make this payment");
	    }

	    // Crear el pago con los detalles del pedido
	    Payment payment = createPayment(order.getPaymentAmount(), order.getCurrency(), order.getMethod(),
	            order.getIntent(), "http://localhost:8081/ezyshop/api" + CANCEL_URL,
	            "http://localhost:8081/ezyshop/api/" + SUCCESS_URL, orderId);

	    return payment;
	}


	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}

	@Transactional
	public void updatePaymentStatus(Long orderId, String paymentId, boolean paymentStatus) {
	    // Busca el pedido por su ID
	    Optional<Order> optionalOrder = orderRepository.findById(orderId);

	    // Si el pedido no existe, lanza una excepción
	    if (!optionalOrder.isPresent()) {
	        throw new OrderNotFoundException("Order not found");
	    }

	    Order order = optionalOrder.get();

	    // Comprueba si el paymentId ya ha sido utilizado en cualquier pedido
	    Optional<Order> optionalOrderWithPaymentId = orderRepository.findByPaymentId(paymentId);
	    if (optionalOrderWithPaymentId.isPresent()) {
	        throw new PaymentIdAlreadyUsedException("PaymentId already used: " + paymentId);
	    }

	    // Establece el paymentId en el pedido
	    order.setPaymentId(paymentId);

	    // Actualiza el estado de pago del pedido
	    order.setPayment(paymentStatus);

	    // Guarda el pedido actualizado en la base de datos
	    orderRepository.updatePaymentId(orderId, paymentId);
        orderRepository.updatePaymentStatus(orderId, paymentStatus);
	}





}
