package com.unitechApi.exception.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class AgeValidator implements ConstraintValidator<BirthDate, LocalDate> {
    public static final int ADULT_AGE = 18;

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param dateOfBirth object to validate
     * @param context     context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        return dateOfBirth != null && LocalDate.now().minusYears(ADULT_AGE).isAfter(dateOfBirth);
    }
}
