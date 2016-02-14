package io.toro.workshop.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( ignoreUnknown = true )
@JsonAutoDetect( fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE )
public class ApiException extends RuntimeException {

    private int httpStatus = 500;
    private Date timestamp = new Date();

    public ApiException() {
    }

    public ApiException( String errorMessage ) {
        super( errorMessage );
    }

    public ApiException( int httpStatus, String errorMessage ) {
        this( errorMessage );
        this.httpStatus = httpStatus;
    }

    public ApiException( Throwable errorMessage ) {
        super( errorMessage );
    }

    public ApiException( int httpStatus, Throwable errorMessage ) {
        this( errorMessage );
        this.httpStatus = httpStatus;
    }

    @JsonProperty( "responseStatus" )
    public int getHttpStatus() {
        return httpStatus;
    }

    @JsonProperty( "message" )
    public String getErrorMessage() {
        // Get root exception message
        Throwable cause = this;
        while ( cause.getCause() != null )
            cause = cause.getCause();
        return cause.getMessage();
    }

    @JsonProperty( "timeStamp" )
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Date timestamp ) {
        this.timestamp = timestamp;
    }

}
