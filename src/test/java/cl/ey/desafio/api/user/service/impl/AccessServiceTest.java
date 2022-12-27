package cl.ey.desafio.api.user.service.impl;


import java.util.EnumMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ErrorEnum;
import cl.ey.desafio.api.user.jpa.security.JwtToken;



@SpringBootTest
public class AccessServiceTest {

	
	@InjectMocks
	private AccessServiceImpl service;
	
	@Mock
	private UserServiceImpl user;
	
	@Value("${api.jwt.secret}")
	private String secret;
	
	@Value("#{${api.enum.msg}}")
	private EnumMap<ErrorEnum, String> msg;
	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(service,"secret",secret);
		ReflectionTestUtils.setField(service,"msg",msg);
	}
	
	@Test
	public void validatUserAccessOK() throws CustomException{
		Mockito.when(user.validateUserCredential(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		JwtToken jwt = service.validatUserAcces("Basic dXNlcjE6c3RyaW5n");
		Assertions.assertNotNull(jwt);
		
		
	}
	
	@Test
	public void validatUserAccessTokenNull() throws CustomException{
		 Exception exception =Assertions.assertThrows(CustomException.class, () -> {
			 service.validatUserAcces(null);
		    });

		    String expectedMessage = "Authorization invalido";
		    String actualMessage = exception.getMessage();
		    Assertions.assertTrue(actualMessage.contains(expectedMessage));
		
		
		
	}
	@Test
	public void validatUserAccessInvalidAuthorization() throws CustomException{
		Exception exception =Assertions.assertThrows(CustomException.class, () -> {
			service.validatUserAcces("Basic etsttokein");
		});
		
		String expectedMessage = "Authorization invalido";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
		
		
		
	}
	@Test
	public void validatUserAccessInvalidCredentials() throws CustomException{
		Mockito.when(user.validateUserCredential(Mockito.anyString(), Mockito.anyString()))
		.thenThrow(new CustomException(ErrorEnum.CREDENCIALES_INVALIDAS,msg.get(ErrorEnum.CREDENCIALES_INVALIDAS)));
		Exception exception =Assertions.assertThrows(CustomException.class, () -> {
			service.validatUserAcces("Basic dGVzdHVzZXI6cGFzc3dvcmR0ZXN0");
		});
		
		String expectedMessage = "Credenciales inválidas";
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
		
		
		
	}
	
	private JwtToken jwtToken() {	
		return new JwtToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTY3MTk5MTI5MCwiaWF0IjoxNjcxOTkwOTkwfQ.d1bQbzt5Zbda5vSsNL3cP5xhhUUrnzpCqKY48uU6dkdhGx3PBChn7ljCDXVfHOLvR7HB2g6w7VoBZQT51a-TzQ");
		
	}
}
