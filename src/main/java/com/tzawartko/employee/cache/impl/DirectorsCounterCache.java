package com.tzawartko.employee.cache.impl;

import com.tzawartko.employee.cache.Cache;
import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.entity.Role;
import com.tzawartko.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DirectorsCounterCache implements Cache {

    @Autowired
    EmployeeRepository employeeRepository;

    private long numberOfDirectors;

    public void incrementDirectors() {
        numberOfDirectors++;
    }

    public void decrementDirectors() {
        numberOfDirectors--;
    }

    public long getCurrentNumberOfDirectors() {
        return numberOfDirectors;
    }

    @Override
    @PostConstruct
    public void init() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        numberOfDirectors = employees.stream()
                .filter(employee -> Role.DIRECTOR.equals(employee.getRole())).count();
    }
}

