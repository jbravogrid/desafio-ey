package cl.ey.desafio.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ResponseError> customException(CustomException ex) {
		switch (ex.getCod()) {
		case USUARIO_NO_SE_PUEDE_CREAR:
			return new ResponseEntity<>(new ResponseError(ex.getMessage()), HttpStatus.CONFLICT);
		case USUARIO_NO_SE_PUEDE_ACTUALIZAR:
			return new ResponseEntity<>(new ResponseError(ex.getMessage()), HttpStatus.NOT_FOUND);
		case CORREO_YA_REGISTRADO:
			return new ResponseEntity<>(new ResponseError(ex.getMessage()), HttpStatus.CONFLICT);
		case CORREO_NO_CUMPLE_FORMATO:
			return new ResponseEntity<>(new ResponseError(ex.getMessage()), HttpStatus.BAD_REQUEST);
		case USUARIO_INACTIVO:
			return new ResponseEntity<>(new ResponseError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
		default:
			return new ResponseEntity<>(new ResponseError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
		@ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ResponseError> handlePasswordValidationException(MethodArgumentNotValidException e) {
			return new ResponseEntity<>(new ResponseError(e.getBindingResult().getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
	       
	    }


}
