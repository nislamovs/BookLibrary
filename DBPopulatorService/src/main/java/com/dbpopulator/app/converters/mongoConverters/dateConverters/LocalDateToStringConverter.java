package com.dbpopulator.app.converters.mongoConverters.dateConverters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.LocalDate;

@WritingConverter
public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    @Override
    public String convert(LocalDate localDate) {

        return localDate.toString();
    }
}