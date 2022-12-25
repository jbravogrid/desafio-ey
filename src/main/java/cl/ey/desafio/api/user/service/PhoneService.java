package cl.ey.desafio.api.user.service;

import java.util.List;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.entity.PhoneEntity;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.model.Phone;

public interface PhoneService {
	
	public List<PhoneEntity>getPhoneByUserId(String id);
	public void addPhones(List<Phone> phones, UserEntity user)throws CustomException;

}
