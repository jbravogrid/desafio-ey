package cl.ey.desafio.api.user.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ErrorEnum;
import cl.ey.desafio.api.user.jwt.JwtUtil;
import cl.ey.desafio.api.user.service.AccessService;
import cl.ey.desafio.api.user.service.UserService;

@Service
public class AccessServiceImpl implements AccessService {
	
	@Value("${api.jwt.expiration-seconds}")
	private long expirationSeconds;

	@Value("${api.crypto.key}")
	private String secret;
	
	@Autowired
	private UserService userService;
	

	@Override
	public String validateAuthorization(String token) throws CustomException {
		if(token != null && token.startsWith("Basic ")) {
			String decodeString = new String(Base64.getDecoder().decode(token.replace("Basic ","")));
			if(decodeString.contains(":")) {
				return decodeString;
			}
		}
		 throw new CustomException(ErrorEnum.SINTAXIS_TOKEN_INCORRECTA, "Sintaxis del token incorrecta");
	}

	@Override
	public boolean validatUserAcces(String user, String pass) throws CustomException {		
		return userService.validateUserAccess(user, pass);
	}

	@Override
	public void saveToken(String name, String token) {
		userService.updateToken(name, token);
				
	}

	@Override
	public String createJwt(String user) {
		return JwtUtil.createJwt(user, secret, expirationSeconds);
		
	}

}
