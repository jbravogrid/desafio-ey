package cl.ey.desafio.api.user.service;

import javax.persistence.NoResultException;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.model.UserRequest;
import cl.ey.desafio.api.user.model.UserResponse;

public interface UserService {
	
	
	public  UserResponse addUser(UserRequest user) throws CustomException; 
	public  UserResponse updateUser(UserRequest user) throws CustomException; 
	public  void updateToken(String name, String token); 
	public UserResponse getUserById(String id) throws NoResultException;
	public boolean userExist(String name) ;
	public boolean  emailExist(String email);	
	public UserResponse getUserByName(String name)throws NoResultException;
	public boolean validateUserAccess(String name, String password) throws CustomException;
	public boolean validateUserToken(String name, String token) throws CustomException;
	
	

}
