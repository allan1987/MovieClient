package com.mnidersoft.movieclient.core.errors;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class ContentNotFoundError extends RuntimeException {

    @Override
    public String getMessage() {
        return "No content available";
    }
}