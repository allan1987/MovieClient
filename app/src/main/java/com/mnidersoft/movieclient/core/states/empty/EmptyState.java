package com.mnidersoft.movieclient.core.states.empty;

import com.mnidersoft.movieclient.core.errors.ContentNotFoundError;
import com.mnidersoft.movieclient.core.states.ErrorMessageFlowable;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class EmptyState<T> implements FlowableTransformer<T, T> {

    EmptyView mView;
    Scheduler mSchedulerUI;

    public EmptyState(EmptyView view, Scheduler schedulerUI) {
        mView = view;
        mSchedulerUI = schedulerUI;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        ErrorMessageFlowable<T> delegate = new ErrorMessageFlowable<>(
                mView.hideEmpty(),
                mView.showEmpty(),
                error -> error instanceof ContentNotFoundError,
                mSchedulerUI
        );

        return upstream.compose(delegate);
    }
}