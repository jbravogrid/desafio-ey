package cl.ey.desafio.api.user.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.entity.PhoneEntity;
import cl.ey.desafio.api.user.jpa.repository.PhoneRepository;
import cl.ey.desafio.api.user.service.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {
	
	@Autowired
	private PhoneRepository repo;

	@Override
	public Set<PhoneEntity> getPhoneByUserId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPhones(Set<PhoneEntity> phones) throws CustomException {
		phones.stream().forEach(p->{
			repo.insertPhone(p);
		});

	}

}
