package com.mnidersoft.movieclient.core.errors;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public interface ErrorVerifier {

    boolean verify(Throwable error);
}
