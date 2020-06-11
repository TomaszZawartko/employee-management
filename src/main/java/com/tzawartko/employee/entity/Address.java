package com.tzawartko.employee.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 6)
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
    private String postalCode;

    @Size(min = 3, max = 64)
    private String street;

    @NotNull
    @NotEmpty
    private Integer houseNr;

    @NotNull
    @NotEmpty
    private Integer flatNr;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToMany(mappedBy = "address", targetEntity = Employee.class)
    private List<Employee> employee;
}
