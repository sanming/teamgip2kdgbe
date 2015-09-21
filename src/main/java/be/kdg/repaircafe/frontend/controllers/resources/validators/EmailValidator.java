package be.kdg.repaircafe.frontend.controllers.resources.validators;

import be.kdg.repaircafe.frontend.controllers.resources.users.UserResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("emailValidator")
public class EmailValidator implements Validator
{
    @Override
    public boolean supports(Class<?> aClass)
    {
        return UserResource.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors)
    {
        // will always succeed because of supports methode
        UserResource userResource = (UserResource) o;

        // old password should be different from new password on password update
        if (userResource.getPassword().equals(userResource.getOldPassword()))
        {
            // mark attributes with errors.
            errors.rejectValue("password", "error.password.nomatch", "Passwords don’t match");
            errors.rejectValue("oldPassword", "error.password.nomatch", "Passwords don’t match");
        }
    }
}
