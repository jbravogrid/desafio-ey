package cl.ey.desafio.api.user.service.impl;

import java.util.Base64;
import java.util.EnumMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ErrorEnum;
import cl.ey.desafio.api.user.jpa.security.JwtToken;
import cl.ey.desafio.api.user.jwt.JwtUtil;
import cl.ey.desafio.api.user.service.AccessService;
import cl.ey.desafio.api.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


@Service
public class AccessServiceImpl implements AccessService {

	private static final String BASIC_PREFIX = "Basic ";
	
	@Value("${api.jwt.secret}")
	private String secret;
	
	@Value("${api.jwt.expiration-seconds}")
	private Long expirationSeconds;
	
	@Value("#{${api.enum.msg}}")
	private EnumMap<ErrorEnum, String> msg;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void saveToken(String name, String token) {
		userService.saveToken(name, token);

	}

	@Override
	public String createJwt(String user) {
		
		return JwtUtil.createJwt(user, secret, expirationSeconds);
	}

	@Override
	public String validateAuthorization(String token) throws CustomException {		
		return authorizationUserName(token)==null ? "NO AUTORIZADO" : "AUTORIZADO";			
		
	}

	@Override
	public JwtToken validatUserAcces(String token) throws CustomException {
		if (token == null || Boolean.FALSE.equals(token.startsWith(BASIC_PREFIX)))
			throw new CustomException(ErrorEnum.AUTHORIZATION_INVALIDO, msg.get(ErrorEnum.AUTHORIZATION_INVALIDO));

		String decodedString = new String(Base64.getDecoder().decode(token.replaceAll(BASIC_PREFIX, "")));
		if (Boolean.FALSE.equals(decodedString.contains(":")))
			throw new CustomException(ErrorEnum.AUTHORIZATION_INVALIDO, msg.get(ErrorEnum.AUTHORIZATION_INVALIDO));
		String name = decodedString.split(":")[0];
		String password = decodedString.split(":")[1];				
		userService.validateUserCredential(name, password);
		JwtToken jwt = new JwtToken(createJwt(name));
		saveToken(name,jwt.getToken());
		return jwt;

	}
	

	@Override
	public String validateAuthorizationUpdate(String token, String name) throws CustomException {		
		if(authorizationUserName(token).equals(name))
			return name;
		throw new CustomException(ErrorEnum.TOKEN_NO_PERTENECE,msg.get(ErrorEnum.TOKEN_NO_PERTENECE));
	}
	
	private String authorizationUserName(String token) throws CustomException {
		final Optional<String> authHeader = Optional.ofNullable(token);
		String autorizado = "";
		if(Boolean.FALSE.equals(authHeader.isPresent()) || Boolean.FALSE.equals(authHeader.get().startsWith("Bearer ")))
			throw new CustomException(ErrorEnum.SINTAXIS_TOKEN_INCORRECTA, "Sintaxis del token incorrecta");
		try {
		final String jwsString = authHeader.get().substring(7);
		final Claims claims = Jwts.parserBuilder() 
			    .setSigningKey(secret)        
			    .build()                   
			    .parseClaimsJws(jwsString).getBody();
		 if(Boolean.FALSE.equals(userService.validaToken(claims.getSubject(), jwsString)))
			 throw new CustomException(ErrorEnum.TOKEN_NO_VALIDO,  msg.get(ErrorEnum.TOKEN_NO_VALIDO));
		autorizado = claims.getSubject();
		}
		catch(ExpiredJwtException e) {
			throw new CustomException(ErrorEnum.TOKEN_EXPIRADO, msg.get(ErrorEnum.TOKEN_EXPIRADO));
		}catch(JwtException e) {
			throw new CustomException(ErrorEnum.TOKEN_NO_VALIDO,  msg.get(ErrorEnum.TOKEN_NO_VALIDO));
		}
		
		return autorizado;
	}
	

}
