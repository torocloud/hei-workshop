package io.toro.workshop.blog.event;

import java.util.concurrent.ExecutorService;

import org.springframework.context.event.EventListener;

import io.toro.workshop.blog.Blog;
import io.toro.workshop.connectors.TwitterConnector;

import twitter4j.TwitterException;

public class BlogEventListener {

    private final TwitterConnector twitterConnector;
    private final ExecutorService executorService;

    private String defaultMessagePrefix = "Created a new blog, titled: ";

    public BlogEventListener( TwitterConnector twitterConnector, ExecutorService executorService ) {
        this.twitterConnector = twitterConnector;
        this.executorService = executorService;
    }

    @EventListener
    public void postToTwitterOnBlogUpdate( final BlogUpdatedEvent blogUpdatedEvent ) {
        executorService.submit( new Runnable() {

            @Override
            public void run() {
                Blog blog = blogUpdatedEvent.getSource();
                try {
                    twitterConnector.tweet( defaultMessagePrefix + blog.getTitle() );
                } catch ( TwitterException ex ) {
                    System.err.println( "Unable to post tweet. Reason: " + ex );
                }
            }
        } );
    }

    void setDefaultMessagePrefix( String message ) {
        this.defaultMessagePrefix = message;
    }
}
