package cl.ey.desafio.api.user.aspect;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import cl.ey.desafio.api.user.exception.CustomException;
import cl.ey.desafio.api.user.exception.ErrorEnum;
import cl.ey.desafio.api.user.model.UserRequest;
import cl.ey.desafio.api.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.security.Keys;

@Aspect
@Component
public class AccessControlAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessControlAspect.class);
	@Autowired
	private UserService userService;
	
	
	@Value("${api.crypto.key}")
	private String secret;
	
	@Around("execution(* cl.ey.desafio.api.user.controller..*(..)) && args(token , user)")
	public Object oauthLog(ProceedingJoinPoint pjp, String token, UserRequest user) throws Throwable {
		StopWatch sw = new StopWatch();
		try {
			sw.start();
			if(Boolean.FALSE.equals(token.startsWith("Bearer "))) {
				throw new CustomException(ErrorEnum.SINTAXIS_TOKEN_INCORRECTA, "Sintaxis incorrecta");
			}
			final String jwt = token.substring(7);
			Jws<Claims> jws;	
			
			try{
				jws = Jwts.parserBuilder()  
					    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))        
					    .build()                   
					    .parseClaimsJws(jwt);
				String name = jws.getBody().getSubject();
				
				if(Boolean.FALSE.equals(userService.validateUserToken(name, jwt)))
					throw new CustomException(ErrorEnum.TOKEN_NO_VALIDO,"El token no es válido");
				
			}catch(ExpiredJwtException e) {
				throw new CustomException(ErrorEnum.TOKEN_EXPIRADO,"El token ha expirado");
			}catch(JwtException e) {
				throw new CustomException(ErrorEnum.TOKEN_NO_VALIDO,"El token no es válido");
			}
		
			return pjp.proceed();
		}finally {
			sw.stop();
			LOGGER.info("OAUTH - timelapsed {} (ms) - {}.{} User:[{}]", sw.getTotalTimeMillis(),
					pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(), user.getName());
		}
	}
}
