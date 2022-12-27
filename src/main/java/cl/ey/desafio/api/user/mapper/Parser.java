package cl.ey.desafio.api.user.mapper;

import java.util.Calendar;
import java.util.UUID;

import cl.ey.desafio.api.user.jpa.entity.PhoneEntity;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.model.Phone;
import cl.ey.desafio.api.user.model.User;
import cl.ey.desafio.api.user.model.UserResponse;

public interface Parser {

	
	public static UserEntity fromAddUserRequest(User input) {
		if(input == null) {
			return null;
		}
		
		UserEntity output = new UserEntity();
		output.setId(UUID.randomUUID().toString());
		output.setName(input.getName());
		output.setPassword(input.getPassword());
		output.setEmail(input.getEmail());
		output.setActive(input.isActive());
		output.setCreated(Calendar.getInstance().getTime());
		output.setLast_login(output.getCreated());
		input.getPhones().stream().forEach(p->{
			PhoneEntity pe = new PhoneEntity();
			pe.setCitycode(p.getCitycode());
			pe.setContrycode(p.getContrycode());
			pe.setNumber(p.getNumber());
			pe.setIdUser(output);
		//	pe.setId(UUID.randomUUID().toString());
			output.getPhones().add(pe);
		});
		return output;
		
	}
	public static UserEntity fromUpdateUserRequest(User input) {
		if(input == null) {
			return null;
		}
		
		UserEntity output = new UserEntity();
		output.setName(input.getName());
		output.setPassword(input.getPassword());
		output.setEmail(input.getEmail());
		output.setActive(input.isActive());		
		output.setModified(Calendar.getInstance().getTime());		
		input.getPhones().stream().forEach(p->{
			PhoneEntity pe = new PhoneEntity();
			pe.setCitycode(p.getCitycode());
			pe.setContrycode(p.getContrycode());
			pe.setNumber(p.getNumber());
			pe.setIdUser(output);			
			output.getPhones().add(pe);
		});
		return output;
		
	}
	
	public static UserResponse fromAddUserEntity(UserEntity input) {
		if(input == null) {
			return null;
		}
		
		UserResponse output = new UserResponse();
		output.setId(input.getId());
		output.setName(input.getName());
		output.setEmail(input.getEmail());
		output.setActive(input.isActive());
		output.setCreated(input.getCreated().toString());
		output.setLastLogin(input.getLast_login().toString());
		output.setToken(input.getToken());
		input.getPhones().stream().forEach(p->{
			Phone ph = new Phone();
			ph.setCitycode(p.getCitycode());
			ph.setContrycode(p.getContrycode());
			ph.setNumber(p.getNumber());
			output.getPhones().add(ph);
		});
		return output;
		
	}	
}
