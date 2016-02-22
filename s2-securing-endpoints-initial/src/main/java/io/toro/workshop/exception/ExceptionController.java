package io.toro.workshop.exception;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController implements ErrorController {

    @ExceptionHandler( ResourceNotFoundException.class )
    ResponseEntity<ApiException> handleNotFound( ResourceNotFoundException ex ) {
        return processApiException( new ApiException( 404, ex ) );
    }

    @ExceptionHandler( HttpMessageNotReadableException.class )
    ResponseEntity<ApiException> handleInvalidRequestBody( HttpMessageNotReadableException ex ) {
        return processApiException( new ApiException( 400, ex ) );
    }

    // TODO Add exception response handler for AuthenticationException

    @ExceptionHandler( ApiException.class )
    ResponseEntity<ApiException> processApiException( ApiException ex ) {
        return ResponseEntity.status( ex.getHttpStatus() ).body( ex );
    }

    @ExceptionHandler( Exception.class )
    ResponseEntity<ApiException> handleAllException( Exception ex ) {
        return processApiException( new ApiException( 500, ex ) );
    }

    @RequestMapping( value = "error" )
    ResponseEntity<ApiException> handleEndpointNotFound( Exception ex ) {
        return handleAllException( ex );
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
