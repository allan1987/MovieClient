package com.mnidersoft.movieclient.core.states.error;

import com.mnidersoft.movieclient.core.errors.ContentNotFoundError;
import com.mnidersoft.movieclient.core.states.ErrorMessageFlowable;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class ErrorState<T> implements FlowableTransformer<T, T> {

    ErrorView mView;
    Scheduler mSchedulerUI;

    public ErrorState(ErrorView view, Scheduler schedulerUI) {
        mView = view;
        mSchedulerUI = schedulerUI;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        ErrorMessageFlowable<T> delegate = new ErrorMessageFlowable<>(
                mView.hideError(),
                mView.showError(),
                error -> !(error instanceof ContentNotFoundError),
                mSchedulerUI
        );

        return upstream.compose(delegate);
    }
}