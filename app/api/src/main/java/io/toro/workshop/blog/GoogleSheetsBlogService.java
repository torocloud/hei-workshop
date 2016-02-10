package io.toro.workshop.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;

import io.toro.workshop.connectors.GoogleSheetsConnector;
import io.toro.workshop.exception.ResourceNotFoundException;

/**
 * Uses GoogleSheets as a data store for blogs
 */
public class GoogleSheetsBlogService implements BlogService {

    private final GoogleSheetsConnector googleSheetsConnector;

    @Autowired
    public GoogleSheetsBlogService( GoogleSheetsConnector googleSheetsConnector ) {
        this.googleSheetsConnector = googleSheetsConnector;
    }

    @Override
    public List<Blog> findBlogs() {
        List<Blog> cells = new ArrayList<>();
        try {
            cells = googleSheetsConnector.getAllCells();
        } catch ( Exception e ) {
            System.err.println( "Oopps! Somethings wrong! " + e.getMessage().toString() );
        }
        return cells;
    }

    @Override
    public Blog findBlog( Long id ) {
        Blog blog = new Blog();
        try {
            blog = googleSheetsConnector.getRowByID( googleSheetsConnector.findIndex( id ) );
        } catch ( Exception e ) {
            throw new ResourceNotFoundException( "Blog not Found!\nMake sure id is correct!" );
        }
        return blog;
    }

    @Override
    public Blog saveBlog( Blog blog ) {
        Blog savedBlog = blog;
        try {
            googleSheetsConnector.addListRow( blog.getId(), blog.getTitle(), blog.getContent() );
            savedBlog = googleSheetsConnector.getRowByID( googleSheetsConnector.findIndex( blog.getId() ) );
        } catch ( Exception e ) {
            System.err.println( "Oopps! Somethings wrong! " + e.getMessage().toString() );
        }
        return savedBlog;
    }

    @Override
    public void deleteBlogById( Long id ) {
        try {
            googleSheetsConnector.deleteRow( googleSheetsConnector.findIndex( id ) );
        } catch ( Exception e ) {
            throw new ResourceNotFoundException( "Blog not Found!\nMake sure id is correct!" );
        }
    }

}
