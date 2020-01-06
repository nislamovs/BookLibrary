package com.dbpopulator.app.configuration.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "Mongo_AuditorProvider")
public class MongoAuditingConfiguration {

    @Bean
    public AuditorAware<String> Mongo_AuditorProvider() {
       return () -> Optional.of("DBPopulatorService");
    }
}
