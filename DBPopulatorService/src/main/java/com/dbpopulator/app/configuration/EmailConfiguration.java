package com.dbpopulator.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;

import static org.apache.commons.lang3.StringUtils.split;

@Configuration
public class EmailConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public SimpleMailMessage alertMessageTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getRequiredProperty("alert.email.from"));
        message.setReplyTo(env.getRequiredProperty("alert.email.reply.to"));
        message.setTo(split(env.getProperty("alert.email.to", ""), ','));
        message.setSubject(env.getRequiredProperty("alert.email.subject.prefix"));

        return message;
    }
}
