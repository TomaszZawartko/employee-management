package com.tzawartko.employee.enumeration.validation;

import com.tzawartko.employee.entity.AddressType;
import com.tzawartko.employee.enumeration.annotation.AddressTypeRestriction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class AddressTypeValidator implements ConstraintValidator<AddressTypeRestriction, AddressType> {
    private AddressType[] subset;

    @Override
    public void initialize(AddressTypeRestriction constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(AddressType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}