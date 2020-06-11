package com.tzawartko.employee.to;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeHierarchyTo {
    EmployeeTo employee;
    EmployeeTo supervisor;
    List<EmployeeTo> subordinates;
}
