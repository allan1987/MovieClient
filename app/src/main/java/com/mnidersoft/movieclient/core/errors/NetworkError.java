package com.mnidersoft.movieclient.core.errors;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class NetworkError extends RuntimeException {

    public NetworkError(String message) {
        super(message);
    }
}
