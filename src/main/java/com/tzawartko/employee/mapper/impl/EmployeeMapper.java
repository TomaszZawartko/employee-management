package com.tzawartko.employee.mapper.impl;

import com.tzawartko.employee.entity.Address;
import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.mapper.Mapper;
import com.tzawartko.employee.repository.AddressRepository;
import com.tzawartko.employee.repository.EmployeeRepository;
import com.tzawartko.employee.to.EmployeeTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper implements Mapper<EmployeeTo, Employee> {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeTo fromEntity(Employee employee) {
        EmployeeTo employeeTo = new EmployeeTo();

        if (employee != null) {
            if (employee.getId() != null) {
                employeeTo.setId(employee.getId());
            }
            employeeTo.setName(employee.getName());
            employeeTo.setSurname(employee.getSurname());
            employeeTo.setPesel(employee.getPesel());
            employeeTo.setRole(employee.getRole());
            List<Long> addressIds = employee.getAddress().stream().map(Address::getId).collect(Collectors.toList());
            employeeTo.setAddressId(addressIds);

            Long supervisorId = employee.getSupervisor() == null ? null : employee.getSupervisor().getId();
            employeeTo.setSupervisorId(supervisorId);
            List<Long> subordinateIds = employee.getSubordinates().stream().map(Employee::getId).collect(Collectors.toList());
            employeeTo.setSubordinateIds(subordinateIds);
        }

        return employeeTo;
    }

    @Override
    public Employee fromTransport(EmployeeTo employeeTo) {
        Employee employee = new Employee();

        employee.setId(employeeTo.getId());
        employee.setName(employeeTo.getName());
        employee.setSurname(employeeTo.getSurname());
        employee.setPesel(employeeTo.getPesel());

        employee.setRole(employeeTo.getRole());
        List<Address> addresses = (List<Address>) addressRepository.findAllById(employeeTo.getAddressId());
        employee.setAddress(addresses);

        Employee supervisor = employeeRepository.findById(employeeTo.getSupervisorId()).orElseThrow();
        employee.setSupervisor(supervisor);

        return employee;
    }
}
