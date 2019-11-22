package com.booklibrary.app.configuration;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfiguration extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {

//        ConverterFactory converterFactory = factory.getConverterFactory();
//        converterFactory.registerConverter(new MyConverter());
//        factory.classMap(Book.class, BookResponse.class).byDefault().register();
    }
}
