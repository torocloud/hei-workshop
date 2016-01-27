package io.toro.workshop.exception;

import java.util.Date;

public class ApiExceptionModel {

    private int httpStatus;
    private String errorMessage;
    private Date timestamp = new Date();

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus( int httpStatus ) {
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage( String errorMessage ) {
        this.errorMessage = errorMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Date timestamp ) {
        this.timestamp = timestamp;
    }

}
