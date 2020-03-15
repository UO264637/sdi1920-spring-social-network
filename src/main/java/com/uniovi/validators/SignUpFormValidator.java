package com.uniovi.validators;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class SignUpFormValidator implements Validator {
	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	/**
	 * Checks in new user:
	 * 		email is not empy or withespaces
	 * 		email is not duplicated
	 * 		name and surname length between 3 and 24 characters
	 * 		password between 6 and 24 characters
	 * 		confirm password coincides with password
	 */
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		if (!user.getEmail().contains("@")) {
			errors.rejectValue("email", "Error.signup.email");
		}
		if (usersService.getUserByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Error.signup.email.duplicate");
		}
		if (user.getName().length() < 3 || user.getName().length() > 24) {
			errors.rejectValue("name", "Error.signup.name.length");
		}
		if (user.getSurname().length() < 3 || user.getSurname().length() > 24) {
			errors.rejectValue("surname", "Error.signup.surname.length");
		}
		if (user.getPassword().length() < 6 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
		}
		if (!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("confirmPassword", "Error.signup.confirmPassword.coincidence");
		}
	}
}