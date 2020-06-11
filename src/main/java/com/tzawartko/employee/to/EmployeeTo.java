package com.tzawartko.employee.to;

import com.tzawartko.employee.entity.Role;
import com.tzawartko.employee.enumeration.annotation.RoleTypeRestriction;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeTo implements Serializable {
    private static final long serialVersionUID = -3939153951563803292L;

    private Long id;

    @NotNull
    @Size(min = 3, max = 64)
    @Pattern(regexp = "[a-zA-Z]+")
    private String name;

    @NotNull
    @Size(min = 3, max = 64)
    @Pattern(regexp = "[a-zA-Z]+")
    private String surname;

    @Size(min = 11, max = 11)
    @Pattern(regexp = "[0-9]+")
    private String pesel;

    @RoleTypeRestriction(anyOf = {Role.DIRECTOR, Role.CEO, Role.EMPLOYEE, Role.MANAGER})
    private Role role;

    private Long supervisorId;

    private List<Long> subordinateIds = new ArrayList<>();

    @NotNull
    @NotEmpty
    private List<Long> addressId = new ArrayList<>();
}
