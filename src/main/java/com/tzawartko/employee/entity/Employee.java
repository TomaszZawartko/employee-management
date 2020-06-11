package com.tzawartko.employee.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tzawartko.employee.serializer.CustomAddressListSerializer;
import com.tzawartko.employee.serializer.CustomEmployeeListSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 64)
    @Pattern(regexp = "[a-zA-Z]+")
    private String name;

    @NotNull
    @Size(min = 3, max = 64)
    @Pattern(regexp = "[a-zA-Z]+")
    private String surname;

    @Size(min = 11, max = 11)
    @Pattern(regexp = "[0-9]+")
    private String pesel;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonSerialize(using = CustomEmployeeListSerializer.class)
    private List<Employee> subordinates;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Address.class)
    @JoinTable(name = "ADDRESS_EMPLOYEE",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "address_id")})
    @JsonSerialize(using = CustomAddressListSerializer.class)
    private List<Address> address;
}
