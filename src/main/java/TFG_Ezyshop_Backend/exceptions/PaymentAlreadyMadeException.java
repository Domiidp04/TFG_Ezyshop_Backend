package TFG_Ezyshop_Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentAlreadyMadeException extends RuntimeException {

	public PaymentAlreadyMadeException(String message) {
        super(message);
    }
}
