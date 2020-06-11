package com.tzawartko.employee.service;

import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.to.EmployeeHierarchyTo;
import com.tzawartko.employee.to.EmployeeTo;

import java.util.List;

public interface EmployeeService {
    List<EmployeeTo> findAll();

    EmployeeTo findById(long id);

    Employee create(EmployeeTo employeeTo);

    Employee update(EmployeeTo employeeTo);

    void delete(Long id);

    EmployeeHierarchyTo findHierarchy(Long id);
}
