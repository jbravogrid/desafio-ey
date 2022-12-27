package cl.ey.desafio.api.user.service;

import java.util.Set;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.entity.PhoneEntity;

public interface PhoneService {
	
	public Set<PhoneEntity>getPhoneByUserId(String id);
	public void addPhones(Set<PhoneEntity> phones)throws CustomException;

}
