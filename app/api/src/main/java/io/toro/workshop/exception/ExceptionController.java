package io.toro.workshop.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController implements ErrorController {

    @ExceptionHandler( ResourceNotFoundException.class )
    ResponseEntity<ApiExceptionModel> handleNotFound( ResourceNotFoundException ex ) {
        ApiExceptionModel model = new ApiExceptionModel();
        model.setHttpStatus( 404 );
        model.setErrorMessage( ex.getMessage() );

        return ResponseEntity.status( 404 ).body( model );
    }

    @ExceptionHandler( Exception.class )
    ResponseEntity<ApiExceptionModel> handleAllException( Exception ex ) {
        ApiExceptionModel model = new ApiExceptionModel();
        model.setHttpStatus( 500 );
        model.setErrorMessage( ex.getMessage() );

        return ResponseEntity.status( 500 ).body( model );
    }

    @RequestMapping( value = "error" )
    ResponseEntity<ApiExceptionModel> handleEndpointNotFound( HttpServletRequest request ) {
        String uri = request.getRequestURI();
        return handleNotFound(
            new ResourceNotFoundException( "No uri " + uri + " found in server" ) );
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
