package com.nixsolutions.bondarenko.study.ws.rest.jsonserializanion;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class CustomDateDeserializer extends StdDeserializer<Date> {

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException {
        String date = jsonparser.getText();
        try {
            return ConcurrentDateFormatAccess.convertStringToDate(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}