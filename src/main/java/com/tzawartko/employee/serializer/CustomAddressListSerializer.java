package com.tzawartko.employee.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tzawartko.employee.entity.Address;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAddressListSerializer extends StdSerializer<List<Address>> {
    protected CustomAddressListSerializer(Class<List<Address>> t) {
        super(t);
    }

    public CustomAddressListSerializer() {
        this(null);
    }

    @Override
    public void serialize(List<Address> addresses, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (Address item : addresses) {
            ids.add(item.getId());
        }
        jsonGenerator.writeObject(ids);
    }
}
