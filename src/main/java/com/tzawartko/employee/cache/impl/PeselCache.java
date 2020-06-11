package com.tzawartko.employee.cache.impl;

import com.tzawartko.employee.cache.Cache;
import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PeselCache implements Cache {

    @Autowired
    EmployeeRepository employeeRepository;

    private Set<String> pesels = new HashSet<>();

    public void addPesel(String pesel){
        pesels.add(pesel);
    }

    public void removePesel(String pesel){
        pesels.remove(pesel);
    }

    public boolean checkIfPeselExsist(String pesel){
        return pesels.contains(pesel);
    }

    @Override
    @PostConstruct
    public void init(){
        List<Employee> employess = (List<Employee>) employeeRepository.findAll();
        pesels.addAll(employess.stream()
                .map(Employee::getPesel).collect(Collectors.toList()));
    }
}
