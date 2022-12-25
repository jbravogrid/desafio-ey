package cl.ey.desafio.api.user.jpa.repository;


import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.ey.desafio.api.user.jpa.entity.UserEntity;

@Repository
public class UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void insertUser(UserEntity user) {
		this.entityManager.persist(user);
	}

	@Transactional
	public void updateUser(UserEntity user) {
		this.entityManager.merge(user);
	}
	
	public UserEntity getUserByName(String name) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(name) = ?1",UserEntity.class);
		return query.setParameter(1, name.toUpperCase(Locale.US)).getSingleResult();
	}
	public UserEntity getUserByCredentials(String name, String password) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(name) = ?1 and password = ?2",UserEntity.class);
		 query.setParameter(1, name.toUpperCase(Locale.US));
		return query.setParameter(2, password).getSingleResult();
	}
	public UserEntity getUserByEmail(String email) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(email) = ?1",UserEntity.class);
		return query.setParameter(1, email.toUpperCase(Locale.US)).getSingleResult();
	}
	public UserEntity getUserByEmailAndNotName(String email, String name) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE Upper(email) = ?1 and Upper(name) <> ?2",UserEntity.class);
		query.setParameter(1, email.toUpperCase(Locale.US));
		return query.setParameter(2, name.toUpperCase(Locale.US)).getSingleResult();
	}
	public UserEntity getUserByNameAndPassword(String name, String password) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE name = ?1 and password =?2",UserEntity.class);
		query.setParameter(1, name);
		return query.setParameter(2, password).getSingleResult();
	}
	
	public UserEntity getUserById(String id) {
		TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT s FROM UserEntity s WHERE id = ?1",UserEntity.class);
		return query.setParameter(1, id).getSingleResult();
	}
}
