package cl.ey.desafio.api.user.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ResponseError;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.model.UserRequest;
import cl.ey.desafio.api.user.model.UserResponse;
import cl.ey.desafio.api.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	@GetMapping(value ="/ey/token", produces = "application/json")
	public ResponseEntity<String>getToken() {
		return  new ResponseEntity<String>(UUID.randomUUID().toString(),HttpStatus.OK);
	}
	
	@GetMapping(value ="/ey/user/{id}", produces = "application/json")
	public ResponseEntity<UserResponse>getUserById(@PathVariable String id) {		
		return  new ResponseEntity<UserResponse>(service.getUserById(id),HttpStatus.OK);
	}
	
	@GetMapping(value ="/ey/user/name/{name}", produces = "application/json")
	public ResponseEntity<UserResponse>getUserByName(@PathVariable String name) {		
		return  new ResponseEntity<UserResponse>(service.getUserByName(name),HttpStatus.OK);
	}
	@PostMapping(value = "/ey/user/add", consumes = "application/json", produces = "application/json")
	public <T> ResponseEntity  addUser(@RequestHeader("Authorization") String token, @RequestBody @Valid UserRequest user) throws CustomException {			
		if(user != null && user.getName() != null)
		return new ResponseEntity<UserResponse>(service.addUser(user),HttpStatus.OK);
		else 
			return new ResponseEntity<ResponseError>(new ResponseError("Test error"),HttpStatus.BAD_REQUEST);
	}
	@PutMapping(value = "/ey/user/update", consumes = "application/json", produces = "application/json")
	public <T> ResponseEntity  updateUser(@RequestBody @Valid UserRequest user) throws CustomException {
		if(user != null && user.getName() != null)
			return new ResponseEntity<UserResponse>(service.updateUser(user),HttpStatus.OK);
		else 
			return new ResponseEntity<ResponseError>(new ResponseError("Test error"),HttpStatus.BAD_REQUEST);
	}

}