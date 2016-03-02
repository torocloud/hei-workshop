package io.toro.workshop.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties( prefix = "connector" )
public class ConnectorProperties {

	//TODO Constructor for Twitter and GoogleSheets
	
	//TODO Getters and Setters for Twitter and GoogleSheets properties
}
