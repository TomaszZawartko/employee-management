package com.tzawartko.employee.validator;

import com.tzawartko.employee.cache.impl.DirectorsCounterCache;
import com.tzawartko.employee.cache.impl.PeselCache;
import com.tzawartko.employee.config.BusinessConfiguration;
import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.entity.Role;
import com.tzawartko.employee.exception.DirectorCountException;
import com.tzawartko.employee.exception.PeselException;
import com.tzawartko.employee.exception.SubordinatesCountToManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeValidator {

    @Autowired
    private PeselCache peselCache;

    @Autowired
    private BusinessConfiguration employeeConfiguration;

    @Autowired
    private DirectorsCounterCache directorsCounterCache;

    public void validateMaxNumbersOfSubordinates(Employee employee) {
        if (employee.getSupervisor().getSubordinates().size() > employeeConfiguration.getMaxSubordinates()) {
            throw new SubordinatesCountToManagerException();
        }
    }

    public void validatePeselIsNotDuplicated(Employee employee) {
        if (peselCache.checkIfPeselExsist(employee.getPesel())) {
            throw new PeselException();
        }
    }

    public void validateMaxNumberOfDirectors(Employee employee) {
        if (Role.DIRECTOR.equals(employee.getRole()) &&
                directorsCounterCache.getCurrentNumberOfDirectors() > employeeConfiguration.getMaxDirectors()) {
            throw new DirectorCountException();
        }
    }
}
