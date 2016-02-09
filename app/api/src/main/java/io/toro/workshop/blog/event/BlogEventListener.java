package io.toro.workshop.blog.event;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import io.toro.workshop.connectors.TwitterConnector;

public class BlogEventListener {

	private final TwitterConnector twitterConnector;
	private final ExecutorService executorService;
	private String defaultMessagePrefix = "Created a new blog: ";

	@Autowired
	public BlogEventListener(TwitterConnector twitterConnector, ExecutorService executorService) {
		this.twitterConnector = twitterConnector;
		this.executorService = executorService;
	}

	@EventListener
	public void postToTwitterOnBlogUpdate(final BlogUpdatedEvent blogUpdatedEvent) {
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				try {
					twitterConnector
							.twitterUpdateStatus(defaultMessagePrefix + blogUpdatedEvent.getSource().getTitle());
				} catch (Exception e) {
					System.err.println("Unable to post tweet. " + e);
				}

			}
		});
	}

	void setDefaultMessagePrefix(String message) {
		this.defaultMessagePrefix = message;
	}
}
