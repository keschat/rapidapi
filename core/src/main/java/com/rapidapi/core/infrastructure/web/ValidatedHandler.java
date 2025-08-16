package com.rapidapi.core.infrastructure.web;

import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;

public interface ValidatedHandler {
    
    default Validator getValidator() {
        return ValidatorHolder.INSTANCE;
    }
    
    // Singleton holder pattern for thread-safe lazy initialization
    class ValidatorHolder {
        private static final Validator INSTANCE;
        
        static {
            ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
                .buildValidatorFactory();
            INSTANCE = factory.getValidator();
        }
    }
}