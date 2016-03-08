package io.toro.workshop.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties( prefix = "connector" )
public class ConnectorProperties {

    private Twitter twitter;
    private GoogleSheets googleSheets;
    
    /*
     * TODO getter and setters for connector classes
     */

    public static class Twitter {

        private String consumerKey;
        private String consumerSecret;
        private String accessToken;
        private String accessTokenSecret;
        
        /*
         * TODO getters and setter for properties
         */
    }

    public static class GoogleSheets {

        private String applicationName;
        private String clientId;
        private String clientSecret;
        private String accessToken;
        private String refreshToken;
        
        /*
         * TODO getters and setter for properties
         */
    }
}
