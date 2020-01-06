package com.dbpopulator.app.converters.mongoConverters.dateConverters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WritingConverter
public class InstantToStringConverter implements Converter<Instant, String> {

    @Override
    public String convert(Instant instant) {
       DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        return DATE_TIME_FORMATTER.format(instant);
    }
}

