package io.toro.workshop.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties( prefix = "connector" )
public class ConnectorProperties {

    private Twitter twitter;
    private GoogleSheets googleSheets;

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter( Twitter twitter ) {
        this.twitter = twitter;
    }

    public GoogleSheets getGoogleSheets() {
        return googleSheets;
    }

    public void setGoogleSheets( GoogleSheets googleSheets ) {
        this.googleSheets = googleSheets;
    }

    public static class Twitter {

        private String consumerKey;
        private String consumerSecret;
        private String accessToken;
        private String accessTokenSecret;

        public String getConsumerKey() {
            return consumerKey;
        }

        public void setConsumerKey( String consumerKey ) {
            this.consumerKey = consumerKey;
        }

        public String getConsumerSecret() {
            return consumerSecret;
        }

        public void setConsumerSecret( String consumerSecret ) {
            this.consumerSecret = consumerSecret;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken( String accessToken ) {
            this.accessToken = accessToken;
        }

        public String getAccessTokenSecret() {
            return accessTokenSecret;
        }

        public void setAccessTokenSecret( String accessTokenSecret ) {
            this.accessTokenSecret = accessTokenSecret;
        }

    }

    public static class GoogleSheets {

        private String applicationName;
        private String clientId;
        private String clientSecret;
        private String accessToken;
        private String refreshToken;

        public void setApplicationName( String applicationName ) {
            this.applicationName = applicationName;
        }

        public String getApplicationName() {
            return applicationName;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId( String clientId ) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret( String clientSecret ) {
            this.clientSecret = clientSecret;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken( String accessToken ) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken( String refreshToken ) {
            this.refreshToken = refreshToken;
        }

    }
}
