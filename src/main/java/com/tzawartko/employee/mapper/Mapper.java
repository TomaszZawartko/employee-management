package com.tzawartko.employee.mapper;

public interface Mapper<T, U> {

    T fromEntity(U entityObject);

    U fromTransport(T transportObject);
}
