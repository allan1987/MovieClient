package com.mnidersoft.movieclient.core.states;

import com.mnidersoft.movieclient.core.states.empty.EmptyState;
import com.mnidersoft.movieclient.core.states.error.ErrorState;
import com.mnidersoft.movieclient.core.states.loading.LoadingState;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorFeedback;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class StatesCoordinator<T> implements FlowableTransformer<T, T> {

    private EmptyState<T> mEmptyState;
    private ErrorState<T> mErrorState;
    private LoadingState<T> mLoadingState;
    private NetworkErrorFeedback<T> mNetworkErrorFeedback;

    public StatesCoordinator(EmptyState<T> emptyState, ErrorState<T> errorState,
                             LoadingState<T> loadingState, NetworkErrorFeedback<T>
                                     networkErrorFeedback) {
        this(errorState, loadingState, networkErrorFeedback);
        mEmptyState = emptyState;
    }

    public StatesCoordinator(ErrorState<T> errorState,
                             LoadingState<T> loadingState, NetworkErrorFeedback<T>
                                     networkErrorFeedback) {
        mErrorState = errorState;
        mLoadingState = loadingState;
        mNetworkErrorFeedback = networkErrorFeedback;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        Flowable<T> flowable = upstream
                .compose(mErrorState)
                .compose(mLoadingState)
                .compose(mNetworkErrorFeedback);

        if (mEmptyState != null) flowable = flowable.compose(mEmptyState);
        return flowable;
    }
}