package cl.ey.desafio.api.user.service.impl;

import java.util.Calendar;
import java.util.EnumMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ErrorEnum;
import cl.ey.desafio.api.user.jpa.entity.PhoneEntity;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.jpa.repository.UserRepository;
import cl.ey.desafio.api.user.mapper.Convert;
import cl.ey.desafio.api.user.mapper.Parser;
import cl.ey.desafio.api.user.model.User;
import cl.ey.desafio.api.user.model.UserResponse;
import cl.ey.desafio.api.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("#{${api.enum.msg}}")
	private EnumMap<ErrorEnum, String> msg;
	
	@Value("#{${api.db.msg}}")
	private EnumMap<ErrorEnum, String> dbmsg;	
	
	
	@Autowired
	private UserRepository repository;
	
	private Convert<User,UserEntity> entity = Parser::fromAddUserRequest;
	private Convert<User,UserEntity> update = Parser::fromUpdateUserRequest;
	private Convert<UserEntity, UserResponse> responseUser = Parser::fromAddUserEntity;
	
	@Override
	public UserResponse addUser(User user) throws CustomException {
		if (validateUserExist(user.getName()))
			throw new CustomException(ErrorEnum.USUARIO_EXISTENTE, dbmsg.get(ErrorEnum.USUARIO_EXISTENTE));
		if (validateIfMailExistsInOtherUser(user.getEmail(), user.getName()))
			throw new CustomException(ErrorEnum.EMAIL_EXISTENTE, dbmsg.get(ErrorEnum.EMAIL_EXISTENTE));
		
		UserEntity userEntity = entity.from(user);

		repository.insertUser(userEntity);		
		return responseUser.from(userEntity);

	}
	
	@Override
	public UserResponse updateUser(User user) throws CustomException {
		if(validateIfMailExistsInOtherUser(user.getEmail(),user.getName()))
			throw new CustomException(ErrorEnum.EMAIL_EXISTE_OTRO_USER, dbmsg.get(ErrorEnum.EMAIL_EXISTE_OTRO_USER));
		UserEntity userEntity = repository.getUserByName(user.getName());	
		userEntity.setPhones(null);
		userEntity.setName(user.getName());
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		userEntity.setActive(user.isActive());		
		userEntity.setModified(Calendar.getInstance().getTime());		
		user.getPhones().stream().forEach(p->{
			PhoneEntity pe = new PhoneEntity();
			pe.setCitycode(p.getCitycode());
			pe.setContrycode(p.getContrycode());
			pe.setNumber(p.getNumber());
			pe.setIdUser(userEntity);			
			userEntity.getPhones().add(pe);
		});
		return responseUser.from(repository.updateUser(userEntity));
	}
		
    public boolean validateUserExist(String name) throws CustomException {
    	try {
    		if(repository.getUserByName(name)!=null)
    			return true;
    	}catch(Exception e) {
    		return false;
    	}
    	return false;
    	
    }
    
    public boolean validateMailExists(String email) throws CustomException {
    	try {
    		if(repository.getUserByEmail(email)!=null)
    			return true;
    	}catch(Exception e) {
    		return false;
    	}
    	return false;
    	
    }
    public boolean validateIfMailExistsInOtherUser(String email, String name) throws CustomException {
    	try {
    		if(repository.getUserByEmailExist(email, name)!=null)
    			return true;
    	}catch(Exception e) {
    		return false;
    	}
    	return false;
    	
    }

	@Override
	public UserResponse getUserById(String id) {		
		return responseUser.from(repository.getUserById(id));
	}

	@Override
	public boolean validateUserCredential(String name, String password) throws CustomException {
		UserEntity user = new UserEntity();		
	try {
		user = repository.getUserByCredentials(name, password);
		
	}catch(Exception e) {
		throw new CustomException(ErrorEnum.CREDENCIALES_INVALIDAS,msg.get(ErrorEnum.CREDENCIALES_INVALIDAS));
	}
	if(Boolean.TRUE.equals(user.isActive()))
		return true;
	
	throw new CustomException(ErrorEnum.USUARIO_INACTIVO,msg.get(ErrorEnum.USUARIO_INACTIVO));
	}

	@Override
	public void saveToken(String name, String token) {
		UserEntity user = repository.getUserByName(name);
		user.setToken(token);
		repository.updateUser(user);
		
	}

	@Override
	public boolean validaToken(String name, String token) throws CustomException {
		UserEntity user = new UserEntity();
		user.setActive(false);
		try {
	
			user = repository.getUserByToken(name, token);
		}
		catch(Exception e) {
			return false;
		}
		if(Boolean.FALSE.equals(user.isActive()))
			throw new CustomException(ErrorEnum.USUARIO_INACTIVO, msg.get(ErrorEnum.USUARIO_INACTIVO));
		return true;
	}

	

}
