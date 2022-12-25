package cl.ey.desafio.api.user.controller;

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
	private AccessService access;

	@GetMapping(value = "/access")
	public ResponseEntity<JwtToken> login(@RequestHeader(value = "Authorization", required = true) String httpRequest)
			throws CustomException {
		String content = access.validateAuthorization(httpRequest);
		String user = content.split(":")[0];
		String pass = content.split(":")[1];
		access.validatUserAcces(user, pass);
		final String jwt = access.createJwt(user);
		access.saveToken(user, jwt );

		return new ResponseEntity<>(new JwtToken(jwt), HttpStatus.OK);

	}

}
