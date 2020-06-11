package com.tzawartko.employee.service.impl;

import com.tzawartko.employee.entity.Employee;
import com.tzawartko.employee.repository.EmployeeRepository;
import com.tzawartko.employee.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmployeeService.class})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class EmployeeServiceImplTest {

    @Autowired
    EmployeeServiceImpl employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void test() {
        //given
        Employee mockedEmployee = Mockito.mock(Employee.class);
        Optional<Employee> optionalOfEmployee = Optional.of(mockedEmployee);
        Mockito.when(employeeRepository.findById(1L)).thenReturn(optionalOfEmployee);

        //when
        employeeService.delete(1L);

        //then
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(1L);
    }
}
