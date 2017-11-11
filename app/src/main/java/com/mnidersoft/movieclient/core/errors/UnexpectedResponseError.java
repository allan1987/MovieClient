package com.mnidersoft.movieclient.core.errors;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class UnexpectedResponseError extends RuntimeException {

    public UnexpectedResponseError(String message) {
        super(message);
    }
}