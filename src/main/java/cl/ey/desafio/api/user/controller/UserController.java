package cl.ey.desafio.api.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.model.User;
import cl.ey.desafio.api.user.model.UserResponse;
import cl.ey.desafio.api.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	

	@PostMapping(value = "/ey/user", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserResponse>  addUser(@RequestHeader("Authorization") String token, @Valid @RequestBody User user) throws CustomException {		
		return new ResponseEntity<UserResponse>(service.addUser(user),HttpStatus.OK);
		
	}
	@PutMapping(value = "/ey/user/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserResponse>  updateUser(@RequestHeader("Authorization") String token, @Valid @RequestBody User user, HttpServletRequest request) throws CustomException {		
		return new ResponseEntity<UserResponse>(service.updateUser(user),HttpStatus.OK);
		
	}

}