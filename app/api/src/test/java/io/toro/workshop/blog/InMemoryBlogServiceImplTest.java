package io.toro.workshop.blog;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.toro.workshop.exception.ResourceNotFoundException;

@Test
public class InMemoryBlogServiceImplTest {

    @Test( expectedExceptions = ResourceNotFoundException.class )
    void testBlogServiceWhenSavingBlogCantManuallySetId() {
        BlogService blogService = new InMemoryBlogServiceImpl();

        Blog blog = new Blog( 999L, "My first blog", "Welcome" );
        blogService.saveBlog( blog );
    }

    @Test
    void testBlogServiceCanCreatesAndUpdates() {
        BlogService blogService = new InMemoryBlogServiceImpl();

        Blog blog = new Blog( null, "My first blog", "Welcome" );
        blog = blogService.saveBlog( blog );

        assertNotNull( blog.getId() );
        assertEquals( blog.getTitle(), "My first blog" );
        assertEquals( blog.getContent(), "Welcome" );

        assertFalse( blogService.findBlogs().isEmpty() );

        blog.setContent( "Updated content" );
        blog = blogService.saveBlog( blog );

        assertEquals( blog.getContent(), "Updated content" );
        assertEquals( blog, blogService.findBlog( blog.getId() ) );
    }

    @Test
    void testBlogServiceCanDelete() {
        BlogService blogService = new InMemoryBlogServiceImpl();
        Blog blog = blogService.saveBlog( new Blog( null, "My first blog", "Welcome" ) );

        Blog found = blogService.findBlog( blog.getId() );
        assertNotNull( found );

        blogService.deleteBlogById( blog.getId() );
        try {
            blogService.findBlog( blog.getId() );
        } catch ( Exception ex ) {
            assertTrue( ex instanceof ResourceNotFoundException );
        }
        blogService.deleteBlogById( blog.getId() );
    }
}
