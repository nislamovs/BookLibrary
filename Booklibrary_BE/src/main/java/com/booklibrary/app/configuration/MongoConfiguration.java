package com.booklibrary.app.configuration;

import com.booklibrary.app.converters.mongoConverters.dateConverters.InstantToStringConverter;
import com.booklibrary.app.converters.mongoConverters.dateConverters.LocalDateToStringConverter;
import com.booklibrary.app.converters.mongoConverters.dateConverters.StringToInstantConverter;
import com.booklibrary.app.converters.mongoConverters.dateConverters.StringToLocalDateConverter;
import com.booklibrary.app.converters.mongoConverters.moneyConverters.MoneyToStringConverter;
import com.booklibrary.app.converters.mongoConverters.moneyConverters.StringToMoneyConverter;
import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import com.mongodb.MongoClient;
import io.github.kaiso.relmongo.config.EnableRelMongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableRelMongo
public class MongoConfiguration {

    @Value(value = "${spring.data.mongodb.database}")           String database;
    @Value(value = "${spring.data.mongock.changelog-path}")     String changeLogScanPath;

    @Bean
    public SpringBootMongock mongock(ApplicationContext springContext, MongoClient mongoClient) {
        return (SpringBootMongock) new SpringBootMongockBuilder(mongoClient, database, changeLogScanPath)
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter> converters = new ArrayList<>();

        converters.add(new InstantToStringConverter());
        converters.add(new StringToInstantConverter());
        converters.add(new LocalDateToStringConverter());
        converters.add(new StringToLocalDateConverter());

        converters.add(new StringToMoneyConverter());
        converters.add(new MoneyToStringConverter());
        return new MongoCustomConversions(converters);
    }
}
