package com.tzawartko.employee.repository;

import com.tzawartko.employee.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
