package com.mnidersoft.movieclient.core.states;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class LoadingFlowable<T> implements FlowableTransformer<T, T> {

    private Action mWhenStart;
    private Action mWhenDone;
    private Scheduler mTargetScheduler;

    public LoadingFlowable(Action whenStart,
                           Action whenDone,
                           Scheduler targetScheduler) {
        this.mWhenStart = whenStart;
        this.mWhenDone = whenDone;
        this.mTargetScheduler = targetScheduler;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.doOnSubscribe(subscription -> show())
                .doOnTerminate(this::hide);
    }

    private void show() {
        subscribeAndFireAction(mWhenStart);
    }

    private void hide() {
        subscribeAndFireAction(mWhenDone);
    }

    private void subscribeAndFireAction(Action toPerform) {
        Completable.fromAction(toPerform)
                .subscribeOn(mTargetScheduler)
                .subscribe();
    }
}