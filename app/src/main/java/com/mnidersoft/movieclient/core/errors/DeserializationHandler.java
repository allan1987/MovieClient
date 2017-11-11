package com.mnidersoft.movieclient.core.errors;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class DeserializationHandler<T> implements FlowableTransformer<T, T> {

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.onErrorResumeNext(this::handleDeserializerError);
    }

    private Publisher<T> handleDeserializerError(Throwable throwable) {
        if (isDeserializationError(throwable)) {
            return Flowable.error(new UnexpectedResponseError("Unknown Deserialization Error"));
        }

        return Flowable.error(throwable);
    }

    private boolean isDeserializationError(Throwable throwable) {
        return throwable instanceof JsonSyntaxException
                || throwable instanceof IllegalStateException
                || throwable instanceof JsonParseException
                || throwable instanceof JsonIOException;
    }
}
