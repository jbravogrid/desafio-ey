package cl.ey.desafio.api.user.hibernate;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import cl.ey.desafio.api.user.jpa.entity.UserEntity;

public class HibernateAnnotationUtil {
	

	public static SessionFactory getSessionFactory(Map<String,String> settings) {

	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	    		.applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")	            
	      .build();

	    Metadata metadata = new MetadataSources(serviceRegistry)
	      .addAnnotatedClass(UserEntity.class)
	      // other domain classes
	      .buildMetadata();

	    return metadata.buildSessionFactory();
	}
	
	
	
}
