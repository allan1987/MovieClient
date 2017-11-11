package com.mnidersoft.movieclient.core.states.networkerror;

import com.mnidersoft.movieclient.core.errors.NetworkError;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class NetworkErrorHandler<T> implements FlowableTransformer<T, T> {

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.onErrorResumeNext(this::handleIfNetworkError);
    }

    private Publisher<T> handleIfNetworkError(Throwable throwable) {
        if (isNetworkError(throwable)) return asNetworkError(throwable);
        return Flowable.error(throwable);
    }

    private Flowable<T> asNetworkError(Throwable throwable) {
        String errorMessage = defineErrorMessage(throwable);
        return Flowable.error(new NetworkError(errorMessage));
    }

    private String defineErrorMessage(Throwable throwable) {
        if (isConnectionTimeout(throwable)) return "Connection timeout";
        if (isRequestCanceled(throwable)) return "Connection interrupted error";
        return "No internet available";
    }

    private boolean isNetworkError(Throwable throwable) {
        return isConnectionTimeout(throwable) || noInternetAvailable(throwable) ||
                isRequestCanceled(throwable);
    }

    private boolean isRequestCanceled(Throwable throwable) {
        return throwable instanceof IOException
                && throwable.getMessage().contentEquals("Canceled");
    }

    private boolean noInternetAvailable(Throwable throwable) {
        return throwable instanceof UnknownHostException;
    }

    private boolean isConnectionTimeout(Throwable throwable) {
        return throwable instanceof SocketTimeoutException;
    }
}