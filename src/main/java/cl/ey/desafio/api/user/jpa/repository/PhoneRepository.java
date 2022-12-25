package cl.ey.desafio.api.user.jpa.repository;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.ey.desafio.api.user.jpa.entity.PhoneEntity;

@Repository
public class PhoneRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void insertPhone(PhoneEntity phone) {
		this.entityManager.persist(phone);
	}
	
	public List<PhoneEntity> getPhoneByUserId(String idUser) {
		TypedQuery<PhoneEntity> query = this.entityManager.createQuery("SELECT p FROM PhoneEntity p  WHERE id_USer = ?1",PhoneEntity.class);
		return query.setParameter(1, idUser).getResultList();
	}
	

}
