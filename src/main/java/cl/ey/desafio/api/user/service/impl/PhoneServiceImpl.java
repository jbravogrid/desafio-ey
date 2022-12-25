package cl.ey.desafio.api.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.entity.PhoneEntity;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.jpa.repository.PhoneRepository;
import cl.ey.desafio.api.user.model.Phone;
import cl.ey.desafio.api.user.service.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {
	
	@Autowired
	private PhoneRepository phoneRepo;
	
	@Override
	public void addPhones(List<Phone> phones, UserEntity user) throws CustomException {
		
		if(phones != null)
		phones.stream().forEach(p->{
			PhoneEntity entity = new PhoneEntity();
			entity.setUser(user);
			entity.setCitycode(p.getCitycode());
			entity.setContrycode(p.getContrycode());
			entity.setNumber(p.getNumber());
			phoneRepo.insertPhone(entity);
			});		
			
				
	}

	@Override
	public List<PhoneEntity> getPhoneByUserId(String id) {		
		return phoneRepo.getPhoneByUserId(id);
	}

}
