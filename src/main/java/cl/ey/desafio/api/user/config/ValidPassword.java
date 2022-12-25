package cl.ey.desafio.api.user.config;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import cl.ey.desafio.api.user.jpa.security.PasswordConstraintValidator;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    

}