package io.toro.workshop.blog.event;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import io.toro.workshop.connectors.TwitterConnector;

public class BlogEventListener {

	@Autowired
	TwitterConnector twitterConnector;

	@Autowired
	ExecutorService executorService;

	@EventListener
	public void postToTwitterOnBlogUpdate(final BlogUpdatedEvent blogUpdatedEvent) {
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				try {
					twitterConnector.twitterUpdateStatus(
							"Create a New Blog: " + blogUpdatedEvent.getSource().getTitle());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

}
