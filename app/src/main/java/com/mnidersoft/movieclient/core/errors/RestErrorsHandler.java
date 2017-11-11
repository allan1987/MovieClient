package com.mnidersoft.movieclient.core.errors;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import retrofit2.HttpException;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class RestErrorsHandler<T> implements FlowableTransformer<T, T> {

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.onErrorResumeNext(this::verifyAndHandleIfRestError);
    }

    private Publisher<T> verifyAndHandleIfRestError(Throwable throwable) {
        if (otherErrorThanNotFound(throwable)) {
            return Flowable.error(new UnexpectedResponseError("Undesired response for this call"));
        }

        if (isNotFoundError(throwable)) return Flowable.error(new ContentNotFoundError());

        return Flowable.error(throwable);
    }

    private boolean otherErrorThanNotFound(Throwable throwable) {
        if (throwable instanceof HttpException) return !isNotFoundError(throwable);

        return false;
    }

    private boolean isNotFoundError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException error = (HttpException) throwable;
            return error.code() == 404;
        }

        return false;
    }
}