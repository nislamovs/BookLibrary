package com.booklibrary.app.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.mongodb.MongoClient;


@Configuration
@EnableMongoAuditing
public class MongoConfiguration {

    @Value(value = "${spring.data.mongodb.database}")           String database;
    @Value(value = "${spring.data.mongock.changeset-path}")     String changesetPath;

    @Bean
    public SpringBootMongock mongock(ApplicationContext springContext, MongoClient mongoClient) {
        return (SpringBootMongock) new SpringBootMongockBuilder(mongoClient, database, changesetPath)
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }
}
