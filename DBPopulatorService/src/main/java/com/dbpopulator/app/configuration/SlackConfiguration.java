package com.dbpopulator.app.configuration;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfiguration {

    @Value(value = "${slack.url}")
    String token;

    @Bean
    public Request slackRequest() {
        return new Request.Builder()
                .addHeader("Content-type", "application/json")
                .url(token).build();
    }

    @Bean
    public OkHttpClient slackCall() {
        return new OkHttpClient();
    }
}
