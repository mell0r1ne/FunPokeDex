package com.truelayer.interview.funpokedex.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotBlank(message = "Pokemon name cannot be blank")
@Size(min = 1, max = 50, message = "Pokemon name must be between 1 and 50 characters")
@Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "Pokemon name can only contain letters, numbers, and hyphens")
public @interface ValidPokemonName {
    String message() default "Invalid Pokemon name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
