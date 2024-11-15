package TFG_Ezyshop_Backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import TFG_Ezyshop_Backend.dto.OrderDto;
import TFG_Ezyshop_Backend.exceptions.OrderNotFoundException;
import TFG_Ezyshop_Backend.repositories.OrderRepository;
import TFG_Ezyshop_Backend.services.OrderService;
import TFG_Ezyshop_Backend.services.PaypalService;

@RestController
public class PaypalController {

	private final PaypalService service;

	public PaypalController(PaypalService service) {
		this.service = service;
	}

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@PostMapping("/payment/{orderId}")
	public ResponseEntity<?> payment(@PathVariable Long orderId){
	    try {
	        // Crear el pago con los detalles del pedido
	        Payment payment = service.createPayment(orderId);

	        // Si el pago es exitoso, actualizar el pedido
	        for (Links link : payment.getLinks()) {
	            if (link.getRel().equals("approval_url")) {
	                return ResponseEntity.ok(link.getHref());
	            }
	        }

	    } catch (PayPalRESTException e) {
	        e.printStackTrace();
	    }
	    return ResponseEntity.badRequest().body("Error processing payment");
	}

	@GetMapping(value = SUCCESS_URL)
	public ResponseEntity<OrderDto> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("orderId") Long orderId) {
	    try {
	        // Ejecuta el pago
	        Payment payment = service.executePayment(paymentId, payerId);

	        // Confirma el pago
	        if (payment.getState().equals("approved")) {
	            // Actualiza el estado de pago en la base de datos
	            service.updatePaymentStatus(orderId, paymentId, true);
	            return new ResponseEntity<>(HttpStatus.OK);
	        }
	    } catch (PayPalRESTException e) {
	        System.out.println(e.getMessage());
	    }
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}





}
