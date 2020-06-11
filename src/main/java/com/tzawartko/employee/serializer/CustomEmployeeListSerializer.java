package com.tzawartko.employee.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tzawartko.employee.entity.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomEmployeeListSerializer extends StdSerializer<List<Employee>> {
    protected CustomEmployeeListSerializer(Class<List<Employee>> t) {
        super(t);
    }

    public CustomEmployeeListSerializer() {
        this(null);
    }

    @Override
    public void serialize(List<Employee> addresses, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (Employee item : addresses) {
            ids.add(item.getId());
        }
        jsonGenerator.writeObject(ids);
    }
}
