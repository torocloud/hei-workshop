package io.toro.workshop.blog.event;

import org.springframework.context.event.EventListener;

public class BlogEventListener  {

    @EventListener
    public void postToTwitterOnBlogUpdate(BlogUpdatedEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
