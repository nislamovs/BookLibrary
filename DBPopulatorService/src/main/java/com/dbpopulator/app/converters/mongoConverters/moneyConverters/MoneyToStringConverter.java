package com.dbpopulator.app.converters.mongoConverters.moneyConverters;

import org.javamoney.moneta.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.math.MathContext;
import java.math.RoundingMode;


@WritingConverter
public class MoneyToStringConverter implements Converter<Money, String> {

    @Override
    public String convert(Money money) {
        return Money.of( money.getNumber().round(new MathContext(4, RoundingMode.HALF_EVEN)),
            money.getCurrency()).toString();
    }
}