package com.dbpopulator.app.services.components;


import com.dbpopulator.app.utils.YamlPropertySourceFactory;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:scripts.yml")
@ConfigurationProperties
@Configuration
public class CurlScripts {

    @Setter
    public String paymentScript = "";

    @Setter
    public String historyScript = "";

    @Setter
    public String visitorAddressDataScript = "";

    @Setter
    public String visitorBankDataScript = "";

    @Setter
    public String visitorPersDataScript = "";

    @Setter
    public String bookShopUrlLink = "";

}
