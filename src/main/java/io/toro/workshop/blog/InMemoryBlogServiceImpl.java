package io.toro.workshop.blog;

import io.toro.workshop.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryBlogServiceImpl implements BlogService {

    private final Map<Long, Blog> blogStore;

    private Long lastInsertedId = 1L;

    public InMemoryBlogServiceImpl() {
        this.blogStore = new LinkedHashMap<>();
    }

    @Override
    public List<Blog> findBlogs() {
        return new ArrayList<>( blogStore.values() );
    }

    @Override
    public Blog findBlog( Long id ) {
        if ( id == null )
            throw new ResourceNotFoundException( "No id provided" );

        Blog storedBlog = blogStore.get( id );
        if ( storedBlog == null )
            throw new ResourceNotFoundException( "No blog was found with id " + id );
        return storedBlog;
    }

    @Override
    public Blog saveBlog( Blog blog ) {
        if ( blog.getId() == null ) {
            // let's "generate" an id for a new blog
            while ( blogStore.containsKey( lastInsertedId ) )
                lastInsertedId++;
            blog.setId( lastInsertedId );
            blogStore.put( lastInsertedId, blog );
        } else {
            Blog storedBlog = findBlog( blog.getId() );
            // this could be simply: blogStore.put( blog.getId(), blog ) as well
            storedBlog.setTitle( blog.getTitle() );
            storedBlog.setContent( blog.getContent() );

            blogStore.put( blog.getId(), storedBlog );
        }
        return blog;
    }

    @Override
    public void deleteBlogById( Long id ) {
        if ( id != null )
            blogStore.remove( id );
    }

}
