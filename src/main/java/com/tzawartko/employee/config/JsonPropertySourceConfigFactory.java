package com.tzawartko.employee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Map;

public class JsonPropertySourceConfigFactory implements PropertySourceFactory {

    @Override
    public MapPropertySource createPropertySource(String name, EncodedResource resource) throws IOException {
        Map readValue = new ObjectMapper()
                .readValue(resource.getInputStream(), Map.class);
        return new MapPropertySource("json-property", readValue);
    }
}

