package cl.ey.desafio.api.user.mapper;

import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.model.UserRequest;
import cl.ey.desafio.api.user.model.UserResponse;

public class UserMapper {

	public static UserResponse mapUser(UserRequest user) {

		UserResponse output = new UserResponse();
		output.setName(user.getName());
		output.setPassword(user.getPassword());
		if (user.getPhones() != null) {
			output.setPhones(user.getPhones());
		}
		return output;
	}

	public static UserResponse mapperUserEntity(UserEntity input) {
		Convert<UserEntity, UserResponse> output = null;
		output = Parser::fromUserEntity;
		return output.from(input);
	}
	
	public static UserEntity mapperUserEntityCreation(UserRequest input) {
		Convert<UserRequest, UserEntity> output = null;
		output = Parser::fromCreateUserRequest;
		return output.from(input);
	}
	
	public static UserEntity mapperUserEntityUpdate(UserRequest input, UserEntity output) {
		Convert<UserRequest, UserEntity> convert = null;
		convert = Parser::fromUpdateUserRequest;
		UserEntity update = convert.from(input);
		output.setActive(update.getActive());
		output.setEmail(update.getEmail());
		output.setPassword(update.getPassword());
		output.setModificationDate(update.getModificationDate());
		return output;
	}
}
