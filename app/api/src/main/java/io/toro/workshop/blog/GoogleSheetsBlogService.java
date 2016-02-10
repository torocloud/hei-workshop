package io.toro.workshop.blog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;

import io.toro.workshop.connectors.GoogleSheetsConnector;
import io.toro.workshop.exception.ResourceNotFoundException;

public class GoogleSheetsBlogService implements BlogService {

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_CONTENT = "Content";

    private final GoogleSheetsConnector googleSheetsConnector;
    private Long lastInsertedId = 1L;

    @Autowired
    public GoogleSheetsBlogService( GoogleSheetsConnector googleSheetsConnector ) {
        this.googleSheetsConnector = googleSheetsConnector;
    }

    @Override
    public List<Blog> findBlogs() {
        try {
            List<ListEntry> rows = googleSheetsConnector.getRowEntries();
            List<Blog> blogs = new ArrayList<>( rows.size() );

            Blog blog = new Blog();
            for ( ListEntry row : rows )
                try {
                    String firstColumn = row.getTitle().getPlainText();
                    blog.setId( Long.valueOf( firstColumn ) );
                    // fetch suceeding columns
                    CustomElementCollection elements = row.getCustomElements();
                    blog.setTitle( elements.getValue( COLUMN_TITLE ) );
                    blog.setContent( elements.getValue( COLUMN_CONTENT ) );

                    blogs.add( blog );
                } catch ( Exception ex ) {
                    // if for some reason the above generates an exception
                    // (e.g.: The id was not a number)
                    // let's just skip it, instead of returning an empty list
                } finally {
                    // reset blog object
                    blog = new Blog();
                }

            return blogs;
        } catch ( Exception e ) {
            System.err.println( "Oopps! Somethings wrong! " + e );
            return new ArrayList<>();
        }
    }

    @Override
    public Blog findBlog( Long id ) {
        if ( id == null )
            throw new ResourceNotFoundException( "Blog id is requried." );

        List<Blog> blogs = findBlogs();
        Blog foundBlog = findBlogWithId( id, blogs );
        if ( foundBlog == null )
            throw new ResourceNotFoundException( "No blog found with id " + id );

        return foundBlog;
    }

    @Override
    public Blog saveBlog( Blog blog ) {
        if ( blog.getId() == null ) {
            // no id means this is a new blog
            List<Blog> blogs = findBlogs();
            Blog foundBlog = findBlogWithId( lastInsertedId, blogs );
            while ( foundBlog != null ) {
                // let's generate and id by incrementing a number 
                // until it doesn't exist in the worksheet
                lastInsertedId++;
                foundBlog = findBlogWithId( lastInsertedId, blogs );
            }
            blog.setId( lastInsertedId );

            try {
                ListEntry entry = new ListEntry();
                entry.getCustomElements().setValueLocal( COLUMN_ID, lastInsertedId.toString() );
                entry.getCustomElements().setValueLocal( COLUMN_TITLE, blog.getTitle() );
                entry.getCustomElements().setValueLocal( COLUMN_CONTENT, blog.getContent() );
                googleSheetsConnector.addRowEntry( entry );
            } catch ( ServiceException | IOException ex ) {
                throw new RuntimeException( "An unexpected error occurred while saving blog. Reason: " + ex );
            }
        } else
            try {
                // try to update to blog using provided id
                Long providedId = blog.getId();

                // we fetch "native" ListEntry so that we can call it's `update` method below
                List<ListEntry> entries = googleSheetsConnector.getRowEntries();
                ListEntry found = findListEntryWithColumn( COLUMN_ID, providedId.toString(), entries );
                if ( found == null )
                    throw new ResourceNotFoundException( "No blog found with id " + providedId );

                found.getCustomElements().setValueLocal( COLUMN_TITLE, blog.getTitle() );
                found.getCustomElements().setValueLocal( COLUMN_CONTENT, blog.getContent() );
                found.update();
            } catch ( ServiceException | IOException ex ) {
                throw new RuntimeException( "An unexpected error occurred while saving blog. Reason: " + ex );
            }
        return blog;
    }

    @Override
    public void deleteBlogById( Long id ) {
        if ( id != null )
            try {
                List<ListEntry> entries = googleSheetsConnector.getRowEntries();
                ListEntry found = findListEntryWithColumn( COLUMN_ID, id.toString(), entries );
                if ( found != null )
                    // No harm, even in repetitively deleting non-existent items. 
                    // This is called Idempotency
                    found.delete();
            } catch ( ServiceException | IOException ex ) {
                // for other errors, let's propagate them
                throw new RuntimeException( "An unexpected error occurred while deleting blog. Reason: " + ex );
            }
    }

    private Blog findBlogWithId( Long id, List<Blog> blogs ) {
        Blog foundBlog = null;
        for ( Blog blog : blogs )
            // obviously inefficient for large records,
            // but will suffice for the current demo
            if ( blog.getId().equals( id ) ) {
                foundBlog = blog;
                break;
            }

        return foundBlog;
    }

    private ListEntry findListEntryWithColumn( String column, String value, List<ListEntry> entries ) {
        ListEntry foundEntry = null;
        for ( ListEntry entry : entries )
            if ( value.equals( entry.getCustomElements().getValue( column ) ) ) {
                foundEntry = entry;
                break;
            }

        return foundEntry;
    }

}
