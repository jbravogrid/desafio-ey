package cl.ey.desafio.api.user.aspect;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cl.ey.desafio.api.user.model.User;
import cl.ey.desafio.api.user.service.AccessService;

@Aspect
@Component
public class UserControllerAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerAspect.class);
	
	@Value("${api.jwt.secret}")
	private String secret;
	
	@Autowired
	private AccessService access;
	
	@Around("execution(* cl.ey.desafio.api.user.controller.*.*(..)) && args(token, user)")
	public Object authentication(ProceedingJoinPoint pjp, String token, User user) throws Throwable {
		StopWatch sw = new StopWatch();
		String autorizado = "NO AUTORIZADO";
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		try {
			sw.start();
			autorizado = access.validateAuthorization(token);
			request.setAttribute("logUser", user.getName());
			return pjp.proceed();
		}finally {
			sw.stop();
			LOGGER.info("timelapsed:{} (ms) - {}.{}(..) - name:[{}] -Acceso:[{}]", 
					sw.getTotalTimeMillis(), pjp.getTarget().getClass().getSimpleName(), 
					pjp.getSignature().getName(), user.getName(), autorizado);
		}
	}
	
	@Around("execution(* cl.ey.desafio.api.user.controller.*.*(..)) && args(token, user, request)")
	public Object authentication(ProceedingJoinPoint pjp, String token, User user, HttpServletRequest request) throws Throwable {
		StopWatch sw = new StopWatch();
		String autorizado = "NO AUTORIZADO";
		try {
			sw.start();
			autorizado = access.validateAuthorizationUpdate(token, user.getName());
			request.setAttribute("logUser", user.getName());
			return pjp.proceed();
		}finally {
			sw.stop();
			LOGGER.info("timelapsed:{} (ms) - {}.{}(..) - name:[{}] -Acceso:[{}]", 
					sw.getTotalTimeMillis(), pjp.getTarget().getClass().getSimpleName(), 
					pjp.getSignature().getName(), user.getName(), autorizado);
		}
	}
	
	@Around("execution(* cl.ey.desafio.api.user.controller.*.*(..)) && args(token)")
	public Object authentication(ProceedingJoinPoint pjp, String token) throws Throwable {
		StopWatch sw = new StopWatch();		
		try {
			sw.start();
			return pjp.proceed();
		}finally {
			sw.stop();
			LOGGER.info("timelapsed:{} (ms) - {}.{}(..) - name:[{}] -Acceso:[{}]", 
					sw.getTotalTimeMillis(), pjp.getTarget().getClass().getSimpleName(), 
					pjp.getSignature().getName(), token);
		}
	}

}
