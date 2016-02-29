package io.toro.workshop.exception;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
     *  Have this method to catch ResourceNotFoundException
     */
    ResponseEntity<ApiException> handleNotFound( ResourceNotFoundException ex ) {
        return processApiException( new ApiException( 404, ex ) );
    }

    /*
     *  TODO Annotate the class with @ExceptionHandler 
     *  and set what type of exception the method will catch
     *  
     *  Have this method to catch HttpMessageNotReadableException
     */
    ResponseEntity<ApiException> handleInvalidRequestBody( HttpMessageNotReadableException ex ) {
        return processApiException( new ApiException( 400, ex ) );
    }

    /*
     *  TODO Annotate the class with @ExceptionHandler 
     *  and set what type of exception the method will catch
     *  
     *  Have this method to catch ApiException
     */
    ResponseEntity<ApiException> processApiException( ApiException ex ) {
        return ResponseEntity.status( ex.getHttpStatus() ).body( ex );
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
        return processApiException( new ApiException( 500, ex ) );
    }

    /*
     *  TODO Annotate the class with @RequestMapping
     *  and set the path the same as the getErrorPath()
     */
    ResponseEntity<ApiException> handleEndpointNotFound( Exception ex ) {
        return handleAllException( ex );
    }

    @Override
    public String getErrorPath() {
        // TODO specify the error path url. 
        throw new UnsupportedOperationException( "Not yet implemented" );
    }
}
