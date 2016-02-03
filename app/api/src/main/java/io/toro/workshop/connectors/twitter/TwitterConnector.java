package io.toro.workshop.connectors.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitter Consumer for HEI Workshop<br/>
 * through the use of Twitter4j Java SDK<br/><br/>
 * 
 * See SDK Resources
 * <a href="http://twitter4j.org/en/api-support.html">Here</a><br/>
 * 
 * See Twitter Public API
 * <a href= "https://dev.twitter.com/rest/public">Here<a/><br/>
 * 
 * @author ericdaluz 
 * @author Yusuke Yamamoto - Twitter4j SDK
 */

public class TwitterConnector {

	/**
	 * Handles Twitter Authorization Process<br/>
	 * Authorize Twitter Connector using ConfigurationBuilder
	 * @param alias the name of the credentials on the property file
	 * @return returns the built configuration
	 */
	private static ConfigurationBuilder authorizeTwitter() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey( "RPkKrwvvG6KszMTG4ISkETygH" )
				.setOAuthConsumerSecret( "P7dKtHUExfP57Kdj7usCB3zMuYwiHxMuCQWEVjkBdl9KmHT5KH" )
				.setOAuthAccessToken( "3142269147-KmeWih2olwDo38c5vZq8u8lYs7PfcYk2Kh5v1gj" )
				.setOAuthAccessTokenSecret( "71yFEdwBlFC3vsgjMGBwh5hzHtRW166i5G6dctss4SHf5" );
		return cb;
	}

	/**
	 * Handles initialization of the Twitter instance<br/>
	 * <a href="http://twitter4j.org/javadoc/twitter4j/api/TimelinesResources.html#getHomeTimeline()">See Here</a>
	 * @param alias the name of the credentials on the property file
	 * @return returns the twitter instance
	 */
	private static Twitter initHandler() {
		TwitterFactory tf = new TwitterFactory( authorizeTwitter().build() );
		Twitter twitter = tf.getInstance();
		return twitter;
	}

	/**
	 * Post an update or tweet into Twitter<br/>
	 * @param alias the name of the credentials on the property file
	 * @param message the tweet or status update
	 * @return returns information about the status
	 */
	public Status twitterUpdateStatus( String message ) throws Exception {
		return initHandler().updateStatus( message );
	}
}
