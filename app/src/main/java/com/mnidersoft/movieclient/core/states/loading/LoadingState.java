package com.mnidersoft.movieclient.core.states.loading;

import com.mnidersoft.movieclient.core.states.LoadingFlowable;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class LoadingState<T> implements FlowableTransformer<T, T> {

    LoadingView mView;
    Scheduler mSchedulerUI;

    public LoadingState(LoadingView view, Scheduler schedulerUI) {
        mView = view;
        mSchedulerUI = schedulerUI;
    }

    @Override public Publisher<T> apply(Flowable<T> upstream) {
        LoadingFlowable<T> delegate = new LoadingFlowable<>(
                mView.showLoading(),
                mView.hideLoading(),
                mSchedulerUI
        );

        return upstream.compose(delegate);
    }
}