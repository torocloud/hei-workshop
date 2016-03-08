package io.toro.workshop.blog.event;

import java.util.concurrent.ExecutorService;

import org.springframework.context.event.EventListener;

import io.toro.workshop.blog.Blog;
import io.toro.workshop.connectors.TwitterConnector;

import twitter4j.TwitterException;

public class BlogEventListener {

	/*
	 * TODO constructors and other initializations
	 */

	@EventListener
	public void postToTwitterOnBlogUpdate(final BlogUpdatedEvent blogUpdatedEvent) {
		
		Blog blog = blogUpdatedEvent.getSource();
		
		/*
		 * TODO call method tweet here
		 */
	}
}
