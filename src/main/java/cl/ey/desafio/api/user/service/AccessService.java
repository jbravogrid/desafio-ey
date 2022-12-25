package cl.ey.desafio.api.user.service;

import cl.ey.desafio.api.user.exception.CustomException;

public interface AccessService {

	
	public String validateAuthorization(String token)throws CustomException;
	public boolean validatUserAcces(String user, String pass)throws CustomException;
	public void saveToken(String name,String token);
	public String createJwt(String user);
}
