package com.test.netcracker.dao.exceptions;

/**
 * Exceptions for dao methods.
 * Created by IPahomov on 16.05.2016.
 */
public class DaoException extends Exception {

    private Exception exception;

    public DaoException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
