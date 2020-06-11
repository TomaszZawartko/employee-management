package com.tzawartko.employee.rest;

import com.tzawartko.employee.cache.impl.DirectorsCounterCache;
import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.entity.Role;
import com.tzawartko.employee.exception.DefaultResponseStatusException;
import com.tzawartko.employee.exception.DeleteEmployeeException;
import com.tzawartko.employee.exception.DirectorCountException;
import com.tzawartko.employee.exception.EmployeeNotFoundException;
import com.tzawartko.employee.exception.PeselException;
import com.tzawartko.employee.exception.SubordinatesCountToManagerException;
import com.tzawartko.employee.service.EmployeeService;
import com.tzawartko.employee.to.EmployeeHierarchyTo;
import com.tzawartko.employee.to.EmployeeTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DirectorsCounterCache directorsCounterCache;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeTo findById(@PathVariable Long id) {
        try {
            return employeeService.findById(id);
        } catch (EmployeeNotFoundException ex) {
            DefaultResponseStatusException.throwEmployeeBadRequestException(
                    "Employee with provided id don't exsist",
                    ex);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee create(@Valid @RequestBody EmployeeTo employeeTo) {
        try {
            Employee savedEmployee = employeeService.create(employeeTo);
            if (Role.DIRECTOR.equals(savedEmployee.getRole())) {
                directorsCounterCache.incrementDirectors();
            }
            return savedEmployee;
        } catch (SubordinatesCountToManagerException ex) {
            DefaultResponseStatusException.throwNotAcceptableParameterException(
                    "Created employee can't be saved, because supervisor has too much subordinates. Please change supervisor.",
                    ex);
        } catch (DirectorCountException ex) {
            DefaultResponseStatusException.throwNotAcceptableParameterException(
                    "Created employee can't be saved, because too many directors exsists. Please change role.",
                    ex);
        } catch (PeselException ex) {
            DefaultResponseStatusException.throwNotAcceptableParameterException(
                    "Created employee can't be saved, because PESEL was duplicated. Please change Pesel.",
                    ex);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        try {
            employeeService.delete(id);
        } catch (EmployeeNotFoundException ex) {
            DefaultResponseStatusException.throwEmployeeBadRequestException(
                    "Employee to deleted with provided id doesn't exsist",
                    ex);
        } catch (DeleteEmployeeException ex) {
            DefaultResponseStatusException.throwNotAcceptableParameterException(
                    "Can't delet employee because he has some subordinates.",
                    ex);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee update(@Valid @RequestBody EmployeeTo employeeTo) {
        try {
            return employeeService.update(employeeTo);
        } catch (EmployeeNotFoundException ex) {
            DefaultResponseStatusException.throwEmployeeBadRequestException(
                    "Employee to update with provided id don't exsist",
                    ex);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "hierarchy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeHierarchyTo employeeHierarchy(@PathVariable Long id) {
        try {
            return employeeService.findHierarchy(id);
        } catch (EmployeeNotFoundException ex) {
            DefaultResponseStatusException.throwEmployeeBadRequestException(
                    "Can't create hierarch, because employee with provided id don't exsist",
                    ex);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
