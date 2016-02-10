package io.toro.workshop.blog.event;

import io.toro.workshop.blog.Blog;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Reijhanniel Jearl Campos
 */
public class BlogUpdatedEvent extends ApplicationEvent {

    public BlogUpdatedEvent( Blog source ) {
        super( source );
    }

    @Override
    public Blog getSource() {
        return ( Blog ) super.getSource();
    }

}
