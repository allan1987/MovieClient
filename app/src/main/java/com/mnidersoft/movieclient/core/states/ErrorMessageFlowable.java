package com.mnidersoft.movieclient.core.states;

import com.mnidersoft.movieclient.core.errors.ErrorVerifier;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class ErrorMessageFlowable<T> implements FlowableTransformer<T, T> {

    private Action mWhenStart;
    private Action mAtError;
    private ErrorVerifier mErrorVerifier;
    private Scheduler mScheduler;

    public ErrorMessageFlowable(Action whenStart, Action atError, ErrorVerifier errorVerifier,
                                Scheduler scheduler) {
        mWhenStart = whenStart;
        mAtError = atError;
        mErrorVerifier = errorVerifier;
        mScheduler = scheduler;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.doOnSubscribe(subscription -> hide())
                .doOnError(this::verifyAndShowIfApplicable);
    }

    private void verifyAndShowIfApplicable(Throwable throwable) {
        if (mErrorVerifier.verify(throwable)) {
            subscribeAndFireAction(mAtError);
        }
    }

    private void hide() {
        subscribeAndFireAction(mWhenStart);
    }

    private void subscribeAndFireAction(Action toPerform) {
        Completable.fromAction(toPerform)
                .subscribeOn(mScheduler)
                .subscribe();
    }
}