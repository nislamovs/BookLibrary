package com.dbpopulator.app.configuration;

import com.dbpopulator.app.converters.mongoConverters.dateConverters.InstantToStringConverter;
import com.dbpopulator.app.converters.mongoConverters.dateConverters.LocalDateToStringConverter;
import com.dbpopulator.app.converters.mongoConverters.dateConverters.StringToInstantConverter;
import com.dbpopulator.app.converters.mongoConverters.dateConverters.StringToLocalDateConverter;
import com.dbpopulator.app.converters.mongoConverters.moneyConverters.MoneyToStringConverter;
import com.dbpopulator.app.converters.mongoConverters.moneyConverters.StringToMoneyConverter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.github.kaiso.relmongo.config.EnableRelMongo;
import lombok.SneakyThrows;
import net.ozwolf.mongo.migrations.MongoTrek;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableRelMongo
public class MongoDBConfiguration { //extends AbstractMongoClientConfiguration {

//    @Value(value = "${spring.data.mongodb.database}")           String database;
//    @Value(value = "${spring.data.mongock.changelog-path}")     String changeLogScanPath;

//    @Bean
//    public SpringBootMongock mongock(ApplicationContext springContext, MongoClient mongoClient) {
//        return (SpringBootMongock) new SpringBootMongockBuilder(mongoClient, database, changeLogScanPath)
//                .setApplicationContext(springContext)
//                .setLockQuickConfig()
//                .build();
//    }

    @Value(value = "${spring.data.mongodb.uri}")                                    String uri;
    @Value(value = "${spring.data.mongodb.database}")                               String database;
    @Value(value = "${application.property.mongotrek.changelog.collection.name}")   String changeLogName;
    @Value(value = "${application.property.mongotrek.script.path}")                 String changeLogLocation;


    @Bean @SneakyThrows
    public MongoTrek store() {
        MongoTrek trek = new MongoTrek(changeLogLocation, uri + database);
        trek.setSchemaVersionCollection(changeLogName);
        trek.migrate();

        return trek;
    }

//    @Override
//    protected String getDatabaseName() {
//        return database;
//    }
//
//    @Override
//    public MongoClient mongoClient() {
////        return MongoClients.create("mongodb://localhost:27017/?replicaSet=rs0&w=majority");
//        return MongoClients.create(uri);
//    }
//
//    @Override
//    public boolean autoIndexCreation() {
//        return false;
//    }

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
