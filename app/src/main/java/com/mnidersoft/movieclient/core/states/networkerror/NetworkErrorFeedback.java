package com.mnidersoft.movieclient.core.states.networkerror;

import com.mnidersoft.movieclient.core.errors.NetworkError;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class NetworkErrorFeedback<T> implements FlowableTransformer<T, T> {

    private NetworkErrorView mView;
    private Scheduler mSchedulerUI;

    public NetworkErrorFeedback(NetworkErrorView view, Scheduler schedulerUI) {
        mView = view;
        mSchedulerUI = schedulerUI;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.doOnError(this::handleIfNetworkError);
    }

    private Publisher<T> handleIfNetworkError(Throwable throwable) {
        if (throwable instanceof NetworkError) {
            Completable.fromAction(mView.showNetworkError())
                    .observeOn(mSchedulerUI)
                    .subscribe();
        }

        return Flowable.error(throwable);
    }
}