package com.dbpopulator.app.services.mongoDump;

import com.dbpopulator.app.utils.YamlPropertySourceFactory;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:shellcmd.yml")
@ConfigurationProperties
@Configuration
public class ShellScripts {

    @Setter
    public String dumpScript = "";

    @Setter
    public String restoreScript = "";
}
