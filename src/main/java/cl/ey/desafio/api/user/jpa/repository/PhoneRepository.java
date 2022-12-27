package cl.ey.desafio.api.user.jpa.repository;


import java.util.HashSet;
import java.util.Set;

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
	
	
	public int deletePhones(String id) {
		return this.entityManager.createQuery("Delete FROM PhoneEntity p WHERE id_user_id = ?1").setParameter(1, id).executeUpdate();
	}
		
	public PhoneEntity getPhoneByUserId(String id) {
		TypedQuery<PhoneEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(id_user) = ?1",PhoneEntity.class);
		return query.setParameter(1, id.toUpperCase()).getSingleResult();
	}
	public Set<PhoneEntity> getPhoneByUserName(String name) {
		TypedQuery<PhoneEntity> query = this.entityManager.createQuery("SELECT u.phones FROM UserEntity u WHERE Upper(name) = ?1",PhoneEntity.class);
		Set<PhoneEntity> listPhones = new HashSet<>();
		query.setParameter(1, name.toUpperCase()).getResultList().stream().forEach(p->{
			listPhones.add(p);
		});
		return listPhones;
	}
}