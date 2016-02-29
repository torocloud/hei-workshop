package io.toro.workshop.blog;

import java.util.List;

public interface BlogService {

    List<Blog> findBlogs();

    Blog findBlog( Long id );

    Blog saveBlog( Blog blog );

    void deleteBlogById( Long id );
}
