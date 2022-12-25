package cl.ey.desafio.api.user.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.jpa.entity.UserEntity;
import cl.ey.desafio.api.user.jpa.repository.UserRepository;
import cl.ey.desafio.api.user.model.UserRequest;
import cl.ey.desafio.api.user.service.PhoneService;


@SpringBootTest()
@ActiveProfiles("test")
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repo;
	
	@Mock
	private	PhoneService phoneService;
	
	@Mock
	private UserRepository repository;;
	
	@Value("#{${app.error}}")
	private Map<Integer,String> errores;
	
	@Value("#{${app.validation.code}}")
	private Map<Integer,String> codeValidation;
	
	@Value("${api.jwt.expiration-seconds}")
	private long expirationSeconds;

	@Value("${api.crypto.key}")
	private String secret;
	
	@Value("${app.email.regex}")
	private String emailRegex;
	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(service, "emailRegex",emailRegex);
		ReflectionTestUtils.setField(service, "errores", errores);
		ReflectionTestUtils.setField(service, "codeValidation", codeValidation);
		ReflectionTestUtils.setField(service, "secret", secret);
		ReflectionTestUtils.setField(service, "expirationSeconds", expirationSeconds);
	}
	
		
	@Test
	public void addUser_EmptyName_field() throws CustomException {
		
		UserRequest request = new UserRequest();
		request.setEmail("testmail@ey.cl");		
	    Exception exception = assertThrows(CustomException.class, () -> {
			service.addUser(request);
		});
		String expectedMessage = "Campo name no puede estar vacio";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void addUser_WrongMailFormat() throws CustomException {
		
		UserRequest request = new UserRequest();
		request.setName("TEST");
		request.setEmail("testmailey.cl");				
		 Exception exception = assertThrows(CustomException.class, () -> {
			 service.addUser(request);
		    });
		String expectedMessage = "El campo email no cumple con el formato";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	@Test
	public void addUser_MailExist() throws CustomException {
		
		UserRequest request = new UserRequest();
		request.setName("TEST");
		request.setEmail("testmail@ey.cl");		
		when(repository.getUserByEmail(Mockito.anyString())).thenReturn(newEntity());
		Exception exception = assertThrows(CustomException.class, () -> {
			service.addUser(request);
		});
		String expectedMessage = "El correo ya registrado";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void addUserOK() throws CustomException {
		
		UserRequest request = new UserRequest();
		request.setName("TEST");
		request.setEmail("testmail@ey.cl");		
		UserEntity user = newEntity();				
		when(repository.getUserByName(Mockito.anyString())).thenReturn(null).thenReturn(user);		
		when(repository.getUserByEmail(Mockito.anyString())).thenReturn(null);		
		doNothing().when(repository).insertUser(Mockito.any(UserEntity.class));	
		
		service.addUser(request);
		// verify(repository, times(1)).insertUser(user);
	}
	
	private UserEntity newEntity() {
		UserEntity user = new UserEntity();
		user.setName("TEST");
		user.setEmail("testmail@ey.cl");
		user.setPassword("TestPass123");
		return user;
				
		
	}
	
	
	
		
}
