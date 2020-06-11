package com.tzawartko.employee.service.impl;

import com.tzawartko.employee.cache.impl.DirectorsCounterCache;
import com.tzawartko.employee.cache.impl.PeselCache;
import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.entity.Role;
import com.tzawartko.employee.exception.DeleteEmployeeException;
import com.tzawartko.employee.exception.EmployeeNotFoundException;
import com.tzawartko.employee.exception.PeselException;
import com.tzawartko.employee.mapper.Mapper;
import com.tzawartko.employee.repository.EmployeeRepository;
import com.tzawartko.employee.service.EmployeeService;
import com.tzawartko.employee.to.EmployeeHierarchyTo;
import com.tzawartko.employee.to.EmployeeTo;
import com.tzawartko.employee.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Mapper<EmployeeTo, Employee> employeeMapper;

    @Autowired
    private EmployeeValidator employeeValidator;

    @Autowired
    private PeselCache peselCache;

    @Autowired
    private DirectorsCounterCache directorsCounterCache;

    @Override
    public List<EmployeeTo> findAll() {
        List<Employee> employess = (List<Employee>) employeeRepository.findAll();
        return employess
                .stream()
                .map(employeeMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeTo findById(long id) {
        return employeeRepository
                .findById(id)
                .map(employeeMapper::fromEntity)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public Employee create(EmployeeTo employeeTo) throws PeselException {
        Employee newEmployee = employeeMapper.fromTransport(employeeTo);
        employeeValidator.validatePeselIsNotDuplicated(newEmployee);
        peselCache.addPesel(newEmployee.getPesel());
        employeeValidator.validateMaxNumberOfDirectors(newEmployee);
        if (Role.DIRECTOR.equals(newEmployee.getRole())) {
            directorsCounterCache.incrementDirectors();
        }
        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee update(EmployeeTo employeeTo) {
        Employee oldEmployee = employeeRepository
                .findById(employeeTo.getId())
                .orElseThrow(EmployeeNotFoundException::new);
        Employee updatedEmployee = employeeMapper.fromTransport(employeeTo);

        employeeValidator.validatePeselIsNotDuplicated(updatedEmployee);
        if (!oldEmployee.getPesel().equals(updatedEmployee.getPesel())) {
            peselCache.removePesel(oldEmployee.getPesel());
            peselCache.addPesel(updatedEmployee.getPesel());
        }

        if (Role.DIRECTOR.equals(oldEmployee.getRole()) && !Role.DIRECTOR.equals(oldEmployee.getRole())) {
            directorsCounterCache.decrementDirectors();
        } else if (!Role.DIRECTOR.equals(oldEmployee.getRole()) && Role.DIRECTOR.equals(oldEmployee.getRole())) {
            directorsCounterCache.incrementDirectors();
        }
        employeeValidator.validateMaxNumberOfDirectors(updatedEmployee);
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void delete(Long id) {
        Employee employeeToDelete = employeeRepository
                .findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        if (employeeToDelete.getSubordinates().isEmpty()) {
            employeeRepository.deleteById(id);
            peselCache.removePesel(employeeToDelete.getPesel());
            if (Role.DIRECTOR.equals(employeeToDelete.getRole())) {
                directorsCounterCache.decrementDirectors();
            }
        } else {
            throw new DeleteEmployeeException();
        }
    }

    @Override
    public EmployeeHierarchyTo findHierarchy(Long id) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        EmployeeTo employeeTo = employeeMapper.fromEntity(employee);
        EmployeeHierarchyTo employeeHierarchyTo = new EmployeeHierarchyTo();
        employeeHierarchyTo.setEmployee(employeeTo);
        EmployeeTo supervisor = employeeMapper.fromEntity(employee.getSupervisor());
        employeeHierarchyTo.setSupervisor(supervisor);
        List<EmployeeTo> subordinates = employee
                .getSubordinates()
                .stream()
                .map(employeeMapper::fromEntity)
                .collect(Collectors.toList());
        employeeHierarchyTo.setSubordinates(subordinates);
        return employeeHierarchyTo;
    }
}
