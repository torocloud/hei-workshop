package io.toro.workshop.connectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import io.toro.workshop.config.properties.ConnectorProperties;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnector implements InitializingBean {

    private ConnectorProperties.Twitter twitterProperties;
    private TwitterFactory twitterFactory;

    @Autowired
    public void setTwitterProperties( ConnectorProperties properties ) {
        this.twitterProperties = properties.getTwitter();
    }

    /**
     * Initialize {@link #twitterFactory} after {@link #twitterProperties} is set.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration config = new ConfigurationBuilder()
                .setOAuthConsumerKey( twitterProperties.getConsumerKey() )
                .setOAuthConsumerSecret( twitterProperties.getConsumerSecret() )
                .setOAuthAccessToken( twitterProperties.getAccessToken() )
                .setOAuthAccessTokenSecret( twitterProperties.getAccessTokenSecret() )
                .build();
        twitterFactory = new TwitterFactory( config );
    }

    public Status tweet( String message ) throws TwitterException {
        return twitterFactory.getInstance().updateStatus( message );
    }
}
