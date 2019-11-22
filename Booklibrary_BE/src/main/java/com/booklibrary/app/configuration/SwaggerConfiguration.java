package com.booklibrary.app.configuration;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Value(value = "${application.developer}")     String developer;
    @Value(value = "${application.support.email}") String email;
    @Value(value = "${application.version}")       String version;
    @Value(value = "${application.description}")   String description;
    @Value(value = "${application.title}")         String title;

    @Bean
    public Docket booksApi() {
        return new Docket(DocumentationType.SPRING_WEB)
                .produces(ImmutableSet.of(MediaType.APPLICATION_JSON_VALUE))
                .consumes(ImmutableSet.of(MediaType.APPLICATION_JSON_VALUE))
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.booklibrary.app"))
                .paths(paths())
                .build();
    }

    private Predicate<String> paths() {
        return regex("/api/.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(new Contact( developer, "", email))
                .version(version).build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
    }
}
