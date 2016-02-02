package io.toro.workshop.blog;

import java.util.List;

/**
 * Uses GoogleSheets as a data store for blogs
 */
public class GoogleSheetsBlogService implements BlogService {

    @Override
    public List<Blog> findBlogs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Blog findBlog(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Blog saveBlog(Blog blog) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteBlogById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
