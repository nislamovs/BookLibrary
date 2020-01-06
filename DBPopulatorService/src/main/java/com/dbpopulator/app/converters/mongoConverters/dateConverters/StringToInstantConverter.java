package com.dbpopulator.app.converters.mongoConverters.dateConverters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@ReadingConverter
public class StringToInstantConverter implements Converter<String, Instant> {
    @Override
    public Instant convert(String source) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        return Instant.from(LocalDateTime.parse(source, DATE_TIME_FORMATTER).atZone(ZoneOffset.UTC));
    }
}
