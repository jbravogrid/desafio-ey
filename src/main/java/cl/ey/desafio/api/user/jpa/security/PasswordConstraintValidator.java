package cl.ey.desafio.api.user.jpa.security;

import java.util.Arrays;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    	 IllegalCharacterRule illegalCharacterRule 
         = new IllegalCharacterRule(new char[] {'&', '<', '>', '%', '.','?','#'});

    	PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
           new LengthRule(8, 30), 
           new UppercaseCharacterRule(1), 
           new DigitCharacterRule(2), 
           new LowercaseCharacterRule(2),
           illegalCharacterRule)
          
         );
        
        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {

            return true;

        }
        
        //Sending one message each time failed validation. 
        constraintValidatorContext.buildConstraintViolationWithTemplate(passwordValidator.getMessages(result).stream().findFirst().get())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        
        return false;
    }
}