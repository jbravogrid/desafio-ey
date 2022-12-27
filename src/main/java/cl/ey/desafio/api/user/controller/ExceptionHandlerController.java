package cl.ey.desafio.api.user.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ResponseError;

@ControllerAdvice
public class ExceptionHandlerController {

	

    @ExceptionHandler(value =  CustomException.class)
    protected ResponseEntity<ResponseError> handleConflict(
    		CustomException ex) {
      switch(ex.getCodigo()) {
      case USUARIO_INACTIVO:
    	  return getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
      case CREDENCIALES_INVALIDAS:
          return getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
      case AUTHORIZATION_INVALIDO:
    	  return getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
      case EMAIL_EXISTENTE:
    	  return getResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
      case EMAIL_EXISTE_OTRO_USER:
    	  return getResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
      case TOKEN_NO_PERTENECE:
    	  return getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
      case TOKEN_EXPIRADO:
    	  return getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
      case TOKEN_NO_VALIDO:
    	  return getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
      case	SINTAXIS_TOKEN_INCORRECTA:  
      	 return getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
      case USUARIO_EXISTENTE:
    	  return getResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
      default:
    	  return getResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)   
    public ResponseEntity<ResponseError>  handlePasswordValidationException(MethodArgumentNotValidException e) {
        //Returning password error message as a response.
        return getResponse(e.getBindingResult().getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);               

    }
    
    @ExceptionHandler(ConstraintViolationException.class)   
    public ResponseEntity<ResponseError>  handleConstraintViolationException(ConstraintViolationException e) {
    	Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    	return getResponse(violations.iterator().next().getMessageTemplate(),HttpStatus.BAD_REQUEST);               
    	
    }
    
    
    private ResponseEntity<ResponseError> getResponse(String msg, HttpStatus status) {
    	return new ResponseEntity<>(new ResponseError(msg), status);
    }
}
