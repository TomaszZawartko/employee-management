package com.tzawartko.employee.rest;

import com.tzawartko.employee.service.EmployeeService;
import com.tzawartko.employee.to.EmployeeTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesRestController {

    @Autowired
    EmployeeService employeeService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeTo> findAll() {
        return employeeService.findAll();
    }
}
