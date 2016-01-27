package io.toro.workshop.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "api/blogs" )
public class BlogApiController {

    private final BlogService blogService;

    @Autowired
    BlogApiController( BlogService blogService ) {
        this.blogService = blogService;
    }

    @RequestMapping( method = RequestMethod.GET )
    ResponseEntity<List<Blog>> getBlogs() {
        List<Blog> blogs = blogService.findBlogs();

        return ResponseEntity.ok( blogs );
    }

    @RequestMapping( value = "{id}", method = RequestMethod.GET )
    ResponseEntity<Blog> getBlog( @PathVariable Long id ) {
        Blog blog = blogService.findBlog( id );

        return ResponseEntity.ok( blog );
    }

    @RequestMapping( method = RequestMethod.POST )
    ResponseEntity<Blog> saveBlog( @RequestBody Blog blog ) {
        blog = blogService.saveBlog( blog );

        return ResponseEntity.ok( blog );
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    ResponseEntity<Void> deleteblog( @PathVariable Long id ) {
        blogService.deleteBlogById( id );

        return ResponseEntity.noContent().build();
    }

}
