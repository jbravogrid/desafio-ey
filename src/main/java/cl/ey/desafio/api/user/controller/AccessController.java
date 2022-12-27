package cl.ey.desafio.api.user.controller;

import java.net.http.HttpHeaders;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.security.JwtToken;
import cl.ey.desafio.api.user.service.AccessService;


@RestController
public class AccessController {
	
	@Autowired
	private AccessService service;
	
	@GetMapping(value ="/ey/access/token", produces = "application/json")
	public ResponseEntity<JwtToken>getToken(@RequestHeader(value = "Authorization", required=true) String token) throws CustomException {
		return  new ResponseEntity<JwtToken>(service.validatUserAcces(token),HttpStatus.OK);
	}
	
	

}