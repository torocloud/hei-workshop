package io.toro.workshop.exception;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *  TODO Annotate the class with @RestController 
 *  to have Spring recognize the class as a Rest Service
 */

/*
 *  TODO Annotate the class with @ControllerAdvice. 
 *  This will make the class applied globally across all @RequestMapping on all
 *  Controller in an application
 */
public class ExceptionController implements ErrorController {

    
    /*
     *  TODO Annotate the class with @ExceptionHandler 
     *  and set what type of exception the method will catch
     *  
     *  Have this method to catch ApiException
     */
    @ExceptionHandler( ApiException.class )
    ResponseEntity<ApiException> processApiException( ApiException ex ) {
        /*
         *  TODO return a ResponseEntity. The body of the ResponseEntity
         *  should contain an APIException.
         *  Make sure the HTTP Status code is appropriate for the exception
         */
        
        // TODO remove the throw exception line once you have implemented the required implementation of the method
        throw new UnsupportedOperationException( "Not yet implemented" );
    }
    
    /*
     *  TODO Annotate the class with @ExceptionHandler 
     *  and set what type of exception the method will catch
     *  
     *  Have this method to catch ResourceNotFoundException
     */
    ResponseEntity<ApiException> handleNotFound( ResourceNotFoundException ex ) {
        /*
         *  TODO return a ResponseEntity. The body of the ResponseEntity
         *  should contain an APIException.
         *  Make sure the HTTP Status code is appropriate for the exception
         */
        
        // TODO remove the throw exception line once you have implemented the required implementation of the method
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    /*
     *  TODO Annotate the class with @ExceptionHandler 
     *  and set what type of exception the method will catch
     *  
     *  Have this method to catch HttpMessageNotReadableException
     */
    ResponseEntity<ApiException> handleInvalidRequestBody( HttpMessageNotReadableException ex ) {
        /*
         *  TODO return a ResponseEntity. The body of the ResponseEntity
         *  should contain an APIException.
         *  Make sure the HTTP Status code is appropriate for the exception
         */
        
        // TODO remove the throw exception line once you have implemented the required implementation of the method
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    /*
     *  TODO Annotate the class with @ExceptionHandler 
     *  and set what type of exception the method will catch
     *  
     *  This will be catching other exception that is not
     *  covered in this class
     *  
     *  Have this method to catch HttpRequestMethodNotSupportedException
     */
    ResponseEntity<ApiException> requestMethodNotSupported( HttpRequestMethodNotSupportedException ex ) {
        /*
         *  TODO return a ResponseEntity. The body of the ResponseEntity
         *  should contain an APIException.
         *  Make sure the HTTP Status code is appropriate for the exception
         */
        
        // TODO remove the throw exception line once you have implemented the required implementation of the method
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    /*
     *  TODO Annotate the class with @ExceptionHandler 
     *  and set what type of exception the method will catch
     *  
     *  This will be catching other exception that is not
     *  covered in this class
     *  
     *  Have this method to catch Exception
     */
    ResponseEntity<ApiException> handleAllException( Exception ex ) {
        /*
         *  TODO return a ResponseEntity. The body of the ResponseEntity
         *  should contain an APIException.
         *  Make sure the HTTP Status code is appropriate for the exception
         */
        
        // TODO remove the throw exception line once you have implemented the required implementation of the method
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    /*
     *  TODO Annotate the class with @RequestMapping
     *  and set the path the same as the getErrorPath()
     */
    ResponseEntity<ApiException> handleEndpointNotFound( Exception ex ) {
        /*
         *  TODO return a ResponseEntity. The body of the ResponseEntity
         *  should contain an APIException.
         *  Make sure the HTTP Status code is appropriate for the exception
         */
        
        // TODO remove the throw exception line once you have implemented the required implementation of the method
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    @Override
    public String getErrorPath() {
        // TODO return a String with the error path url. 
        
        // TODO remove the throw exception line once you have implemented the required implementation of the method
        throw new UnsupportedOperationException( "Not yet implemented" );
    }
}
