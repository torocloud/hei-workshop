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
	
	/*
	 * TODO constructors and other initialization here
	 */

    /**
     * Initialize {@link #twitterFactory} after {@link #twitterProperties} is set.
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration config = new ConfigurationBuilder()
                .setOAuthConsumerKey(  null /*insert getter for Consumer Key*/ )
                .setOAuthConsumerSecret( null /*insert getter for Consumer Secret*/ )
                .setOAuthAccessToken( null /*insert getter for Access Token*/ )
                .setOAuthAccessTokenSecret( null /*insert getter for Token Secret*/ )
                .build();
        twitterFactory = new TwitterFactory( config );
    }

   /*
    * TODO method for a tweet update
    * <a href= "http://twitter4j.org/en/code-examples.html">See Example Here</a>
    */
}
