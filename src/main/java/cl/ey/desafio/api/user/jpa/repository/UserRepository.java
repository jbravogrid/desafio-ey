package cl.ey.desafio.api.user.jpa.repository;


import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.ey.desafio.api.user.jpa.entity.UserEntity;

@Repository
public class UserRepository  {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PhoneRepository phoneRepo;
	
	@Transactional(rollbackFor = {ConstraintViolationException.class})
	public void insertUser(UserEntity user)  {
		this.entityManager.persist(user);
		user.getPhones().stream().forEach(p->{
			phoneRepo.insertPhone(p);
		});
	}
	
	@Transactional(rollbackFor = {ConstraintViolationException.class})
	public UserEntity updateUser(UserEntity user) {
		int del = phoneRepo.deletePhones(user.getId());
		user.getPhones().stream().forEach(p->{
			phoneRepo.insertPhone(p);
		});
		return  this.entityManager.merge(user);	
		
		
	}
	
	
	
	
	public UserEntity getUserById(String id) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE id = ?1",UserEntity.class);
		return query.setParameter(1, id).getSingleResult();
	}
	public UserEntity getUserByName(String name) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(name) = ?1",UserEntity.class);
		return query.setParameter(1, name.toUpperCase(Locale.US)).getSingleResult();
	}
	public UserEntity getUserByEmail(String email) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(email) in ?1",UserEntity.class);
		return query.setParameter(1, email.toUpperCase(Locale.US)).getSingleResult();
	}
	public UserEntity getUserByEmailExist(String email, String name) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(email) in ?1 and Upper(name) <> ?2",UserEntity.class);
		query.setParameter(1, email.toUpperCase(Locale.US));
		return query.setParameter(2, name.toUpperCase(Locale.US)).getSingleResult();
	}
	
	public UserEntity getUserByCredentials(String name, String password) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(name) = ?1 and password = ?2",UserEntity.class);
		query.setParameter(1, name.toUpperCase(Locale.US));
		return query.setParameter(2, password).getSingleResult();
	}
	
	public UserEntity getUserByToken(String name, String token){
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(name) = ?1 and token = ?2",UserEntity.class);
		query.setParameter(1, name.toUpperCase(Locale.US));
		return query.setParameter(2, token).getSingleResult();
	}

	
	
}
	