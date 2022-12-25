package cl.ey.desafio.api.user.mapper;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.model.Phone;
import cl.ey.desafio.api.user.model.UserRequest;
import cl.ey.desafio.api.user.model.UserResponse;

public interface Parser {

	public static UserResponse fromUserEntity(UserEntity entity) {
		UserResponse response = new UserResponse();
		response.setId(entity.getId());
		response.setName(entity.getName());
		response.setEmail(entity.getEmail());
		response.setPassword(entity.getPassword());
		response.setCreationDate(entity.getCreationDate() == null ? null : parseFecha(entity.getCreationDate()));
		response.setId(entity.getId());
		response.setLastLogin(entity.getLastLogin() == null ? null : parseFecha(entity.getLastLogin()));
		response.setModificationDate(entity.getModificationDate()== null ? null :parseFecha(entity.getModificationDate()));
		response.setToken(entity.getToken());
		response.setActive(entity.getActive()!= null ? entity.getActive() : false);
		entity.getPhones().stream().forEach(p->{
			Phone phone = new Phone(p.getNumber(), p.getCitycode(), p.getContrycode());
			response.getPhones().add(phone);
		});		
		return response;
		
	}
	public static UserEntity fromCreateUserRequest(UserRequest request) {
		UserEntity response = new UserEntity();		
		response.setName(request.getName());
		response.setEmail(request.getEmail());
		response.setPassword(request.getPassword());
		response.setCreationDate(Date.from(Instant.now()));
		response.setId(UUID.randomUUID().toString());
		response.setLastLogin(Date.from(Instant.now()));
		response.setToken(UUID.randomUUID().toString());
		response.setActive(request.isActive());		
		return response;
		
	}
	public static UserEntity fromUpdateUserRequest(UserRequest request) {
		UserEntity response = new UserEntity();			
		response.setEmail(request.getEmail());
		response.setPassword(request.getPassword());
		response.setModificationDate(Date.from(Instant.now()));				
		response.setActive(request.isActive());
		
		return response;
		
	}
	
	public static String parseFecha(Date fecha) {
		return DateFormat
		  .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
		  .format(fecha);
	}

}
