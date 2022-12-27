package cl.ey.desafio.api.user.service;

import org.springframework.stereotype.Service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.model.User;
import cl.ey.desafio.api.user.model.UserResponse;

@Service
public interface UserService {
	
	
	public  UserResponse addUser(User user)throws CustomException; 
	public  UserResponse updateUser(User user)throws CustomException; 
	public UserResponse getUserById(String id);
	public boolean validateUserCredential(String name, String password) throws CustomException;
	public void saveToken(String name, String token);
	public boolean validaToken(String name, String token)throws CustomException;

}
