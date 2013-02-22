package com.ipetruk.restfulcsv.util;

public class ApplicationInitializatioException extends RuntimeException{
    public ApplicationInitializatioException() {
    }

    public ApplicationInitializatioException(String message) {
        super(message);
    }

    public ApplicationInitializatioException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationInitializatioException(Throwable cause) {
        super(cause);
    }

    public ApplicationInitializatioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
