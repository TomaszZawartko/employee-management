package com.tzawartko.employee.enumeration.validation;

import com.tzawartko.employee.entity.Role;
import com.tzawartko.employee.enumeration.annotation.RoleTypeRestriction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RoleTypeValidator implements ConstraintValidator<RoleTypeRestriction, Role> {
    private Role[] subset;

    @Override
    public void initialize(RoleTypeRestriction constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Role value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}