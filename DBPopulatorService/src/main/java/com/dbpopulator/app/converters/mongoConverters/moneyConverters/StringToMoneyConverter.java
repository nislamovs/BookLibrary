package com.dbpopulator.app.converters.mongoConverters.moneyConverters;

import org.javamoney.moneta.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToMoneyConverter implements Converter<String, Money> {

    @Override
    public Money convert(String moneyString) {
        return Money.parse(moneyString);
    }
}
