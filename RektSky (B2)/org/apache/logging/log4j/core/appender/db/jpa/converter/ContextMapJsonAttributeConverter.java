package org.apache.logging.log4j.core.appender.db.jpa.converter;

import java.util.*;
import com.fasterxml.jackson.databind.*;
import javax.persistence.*;
import java.io.*;
import org.apache.logging.log4j.core.helpers.*;
import com.fasterxml.jackson.core.type.*;

@Converter(autoApply = false)
public class ContextMapJsonAttributeConverter implements AttributeConverter<Map<String, String>, String>
{
    static final ObjectMapper OBJECT_MAPPER;
    
    public String convertToDatabaseColumn(final Map<String, String> contextMap) {
        if (contextMap == null) {
            return null;
        }
        try {
            return ContextMapJsonAttributeConverter.OBJECT_MAPPER.writeValueAsString((Object)contextMap);
        }
        catch (IOException e) {
            throw new PersistenceException("Failed to convert map to JSON string.", (Throwable)e);
        }
    }
    
    public Map<String, String> convertToEntityAttribute(final String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }
        try {
            return (Map<String, String>)ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(s, (TypeReference)new TypeReference<Map<String, String>>() {});
        }
        catch (IOException e) {
            throw new PersistenceException("Failed to convert JSON string to map.", (Throwable)e);
        }
    }
    
    static {
        OBJECT_MAPPER = new ObjectMapper();
    }
}
