package com.definexpracticum.assignmentthree.validation;

import com.definexpracticum.assignmentthree.model.OutgoingRequestForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



public class CommaDelimitedLocationValidator implements Validator {

    // CommaDelimitedLocationValidator -> Validates form input 'location'.
    // 'location' -> Can be at max three comma seperated words.
    private static final String PATTERN = "^([A-Za-z]+,[A-Za-z]+,[A-Za-z]+|[A-Za-z]+,[A-Za-z]+|[A-Za-z]+)$";

    @Override
    public boolean supports(Class<?> clazz) {
        return OutgoingRequestForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "Empty location");
        OutgoingRequestForm outgoingRequestForm = (OutgoingRequestForm) target;
        if (!outgoingRequestForm.getLocation().matches(PATTERN)) {
            errors.rejectValue("location", "commaDelimitedLocation: String does not match pattern");
        }
    }
}
