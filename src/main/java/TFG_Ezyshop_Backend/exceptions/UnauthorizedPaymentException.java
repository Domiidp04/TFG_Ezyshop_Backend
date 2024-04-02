package TFG_Ezyshop_Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnauthorizedPaymentException extends RuntimeException {

	public UnauthorizedPaymentException(String message) {
        super(message);
    }
}
