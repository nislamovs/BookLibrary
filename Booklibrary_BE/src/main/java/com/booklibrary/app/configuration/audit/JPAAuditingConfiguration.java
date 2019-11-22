package com.booklibrary.app.configuration.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "JPA_AuditorProvider")
public class JPAAuditingConfiguration {

    @Bean
    public AuditorAware<String> JPA_AuditorProvider() {
        /*
          if you are using spring security, you can get the currently logged username with following code segment.
          SecurityContextHolder.getContext().getAuthentication().getName()
         */

        return () -> Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
