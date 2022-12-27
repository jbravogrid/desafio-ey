package cl.ey.desafio.api.user.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import cl.ey.desafio.api.user.jpa.repository.UserRepository;

@SpringBootTest
public class UserServiceImplTest {
	
	
	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repo;

}
