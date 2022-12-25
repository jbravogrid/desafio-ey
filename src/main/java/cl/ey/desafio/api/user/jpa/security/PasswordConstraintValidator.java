package cl.ey.desafio.api.user.jpa.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.UppercaseCharacterRule;
import cl.ey.desafio.api.user.config.ValidPassword;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> { 

	 @Override
	    public void initialize(ValidPassword arg0) {
		
	    }

	    @Override
	    public boolean isValid(String password, ConstraintValidatorContext context) {
	    	 URL resource = this.getClass().getClassLoader().getResource("messages.properties");
				Properties props = new Properties();
				try {
					props.load(new FileInputStream(resource.getPath()));
				} catch (IOException e) {					
					e.printStackTrace();
				}			
			
				MessageResolver resolver = new PropertiesMessageResolver(props);
	        PasswordValidator validator = new PasswordValidator(resolver,Arrays.asList(
	           new LengthRule(8, 30), 
	           new UppercaseCharacterRule(1),	        
	           new LowercaseCharacterRule(2),
	           new DigitCharacterRule(2)
	           
	          ));

	        RuleResult result = validator.validate(new PasswordData(password));
	        if (result.isValid()) {
	            return true;
	        }
	        context.buildConstraintViolationWithTemplate(validator.getMessages(result).stream().findFirst().get())
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
    
    return false;
	    }
	}
