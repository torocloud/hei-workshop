package io.toro.workshop.blog;

import java.util.List;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/*
 *  TODO Annotate the class with @RestController 
 *  to have Spring recognize the class as a Rest Service
 */
/*
 *  TODO Annotate the class with @RequestMapping 
 *  and set the base url of your rest service as "api/blogs"
 */
public class BlogApiController {
    
    /*
     * BlogService is the object that performs the business logic
     */
    private final BlogService blogService;
    
    /*
     * Spring automatically use dependency injection to inject the correct Concrete implementation of the BlogService
     */
    @Autowired
    BlogApiController( BlogService blogService ) {
        this.blogService = blogService;
    }
    
    /*
     * TODO Annotate this method with @RequestMapping and set the suitable HTTP Method for the service
     */
    ResponseEntity<List<Blog>> getBlogs() {
        // TODO Use blogService to get all blogs entries
        throw new UnsupportedOperationException( "Not yet implemented" );
        // TODO Return the blog entries with the suitable HTTPStatus
    }
    
    /*
     * TODO Annotate this method with @RequestMapping with a value of {id} and set the suitable HTTP Method
     */
    ResponseEntity<Blog> getBlog( /* TODO annotate your parameter with @PathVariable annotation. */ Long id ) {
        // TODO Use blogService to get blog entry via id
        throw new UnsupportedOperationException( "Not yet implemented" );
        // TODO Return the blog entries with the suitable HTTPStatus
    }
    
    /*
     * TODO Annotate this method with @RequestMapping and set the suitable HTTP Method for the service
     */
    ResponseEntity<Blog> saveBlog( /* TODO annotate your parameter with @RequestBody annotation */ Blog blog,
            UriComponentsBuilder uriBuilder ) {
        // TODO Use blogService to save the incoming blog from client
        throw new UnsupportedOperationException( "Not yet implemented" );
        // TODO Return the blog entries with the suitable HTTPStatus
    }
    
    /*
     * TODO Annotate this method with @RequestMapping with a value of {id} and set the suitable HTTP Method
     */
    ResponseEntity<Void> deleteblog( /* TODO annotate your parameter with @PathVariable annotation. */ Long id ) {
        // TODO Use blogService to delete the blog entry via id
        throw new UnsupportedOperationException( "Not yet implemented" );
        // TODO Return a empty response with the suitable HTTPStatus
    }
    
}
