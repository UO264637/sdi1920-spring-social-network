package com.uniovi.validators;

import com.uniovi.entities.Publication;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class PublicationsValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Publication.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Publication publication = (Publication) target;

		if (publication.getTitle().length() < 5 || publication.getTitle().length() > 24) {
			errors.rejectValue("title", "Error.publication.title.length");
		}
		if (publication.getText().length() < 5 || publication.getText().length() > 200) {
			errors.rejectValue("text", "Error.publication.text.length");
		}
	}
}