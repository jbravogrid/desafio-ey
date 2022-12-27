package cl.ey.desafio.api.user.service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.security.JwtToken;

public interface AccessService {

	
	public String validateAuthorization(String token)throws CustomException;
	public String validateAuthorizationUpdate(String token, String name)throws CustomException;
	public JwtToken validatUserAcces(String token)throws CustomException;
	public void saveToken(String name,String token);
	public String createJwt(String name);
	
}
