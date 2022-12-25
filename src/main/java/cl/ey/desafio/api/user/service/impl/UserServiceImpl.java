package cl.ey.desafio.api.user.service.impl;

import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ErrorEnum;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.jpa.repository.UserRepository;
import cl.ey.desafio.api.user.jwt.JwtUtil;
import cl.ey.desafio.api.user.mapper.UserMapper;
import cl.ey.desafio.api.user.model.UserRequest;
import cl.ey.desafio.api.user.model.UserResponse;
import cl.ey.desafio.api.user.service.PhoneService;
import cl.ey.desafio.api.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("#{${app.error}}")
	private Map<Integer,String> errores;
	
	@Value("#{${app.validation.code}}")
	private Map<Integer,String> codeValidation;
	
	@Value("${app.email.regex}")
	private String emailRegex;
	
	@Value("${api.jwt.expiration-seconds}")
	private long expirationSeconds;

	@Value("${api.crypto.key}")
	private String secret;
	@Autowired
	private PhoneService phoneService;
	
	@Autowired
	private UserRepository repository;
	
	
	@Override
	public UserResponse addUser(UserRequest user) throws CustomException {
		{
				
			validation(user);			
			UserEntity entity = UserMapper.mapperUserEntityCreation(user);
			String token = JwtUtil.createJwt(user.getName(), secret, expirationSeconds);
			entity.setToken(token);
			repository.insertUser(entity);	
			phoneService.addPhones(user.getPhones(),entity);
			return getUserByName(user.getName());
		}
	}

	@Override
	public UserResponse updateUser(UserRequest user) throws CustomException {
		if(Boolean.FALSE.equals(userExist(user.getName())))
			throw new CustomException(ErrorEnum.USUARIO_NO_SE_PUEDE_ACTUALIZAR, errores.get(2));
		UserEntity entityToUpdate = repository.getUserByName(user.getName());
		entityToUpdate = UserMapper.mapperUserEntityUpdate(user,entityToUpdate);	
		repository.updateUser(entityToUpdate);	
		phoneService.addPhones(user.getPhones(),entityToUpdate);
		return getUserById(entityToUpdate.getId());		
	}
	@Override
	public UserResponse getUserById(String id) throws NoResultException{	
		UserEntity user = repository.getUserById(id);		
		user.getPhones().addAll( phoneService.getPhoneByUserId(id));
		return UserMapper.mapperUserEntity(repository.getUserById(id));
	}

	@Override
	public UserResponse getUserByName(String name) throws NoResultException{
		UserEntity user = repository.getUserByName(name);		
		user.getPhones().addAll(phoneService.getPhoneByUserId(user.getId()));
		return UserMapper.mapperUserEntity(user);
	}

	@Override
	public boolean userExist(String name)  {
		try{
			UserEntity user = repository.getUserByName(name);
			if(user != null)
			return true;
		}catch(Exception e) {
			return false;
		}
		return false;
	}
	
	@Override
	public boolean emailExist(String email)  {
		try{
			UserEntity user = repository.getUserByEmail(email);
			if(user != null)
				return true;
		}catch(Exception e) {
			return false;
		}
		return false;
	}	
	
	
	@Override
	public boolean validateUserAccess(String name, String password) throws CustomException {
		UserEntity user = new UserEntity();
		user.setActive(false);
		try{
			 user = repository.getUserByCredentials(name,password);			
		}catch(Exception e) {
			 throw new CustomException(ErrorEnum.CREDENCIALES_INVALIDAS, errores.get(6));
		}
		if(Boolean.FALSE.equals(user.getActive()))
			throw new CustomException(ErrorEnum.USUARIO_INACTIVO, errores.get(5));	
		return true;
		
		
	}
	
	public void validation(UserRequest user) throws CustomException {
		if (Boolean.TRUE.equals(user.getName()==null))
			throw new CustomException(ErrorEnum.USUARIO_NAME_VACIO, codeValidation.get(-1));
		if (Boolean.FALSE.equals(patternMatches(user.getEmail(), emailRegex)))
			throw new CustomException(ErrorEnum.CORREO_NO_CUMPLE_FORMATO, codeValidation.get(4));
		if (Boolean.TRUE.equals(userExist(user.getName())))
			throw new CustomException(ErrorEnum.USUARIO_NO_SE_PUEDE_CREAR, errores.get(1));
		if (Boolean.TRUE.equals(emailExist(user.getEmail())))
			throw new CustomException(ErrorEnum.CORREO_YA_REGISTRADO, codeValidation.get(3));

	}
	
	public static boolean patternMatches(String field, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(field)
	      .matches();
	}

	@Override
	public boolean validateUserToken(String name, String token) throws CustomException {
		boolean valid = false;
		UserEntity user = new UserEntity();
		try {
			user = repository.getUserByName(name);
		} catch (Exception e) {
			throw new CustomException(ErrorEnum.USUARIO_NO_VALIDADO, errores.get(7));
		}
		if (Boolean.FALSE.equals(user.getActive()))
			throw new CustomException(ErrorEnum.USUARIO_INACTIVO, errores.get(5));
		if (user.getToken().equals(token))
			valid = true;
		return valid;
	}

	@Override
	public void updateToken(String name, String token) {
		UserEntity entityToUpdate = repository.getUserByName(name);
		entityToUpdate.setToken(token);
		repository.updateUser(entityToUpdate);	
		
	}

			

}
