package com.mnidersoft.movieclient.presentation.main;

import com.mnidersoft.movieclient.core.states.empty.EmptyView;
import com.mnidersoft.movieclient.core.states.error.ErrorView;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorView;
import com.mnidersoft.movieclient.model.MoviesResponse;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public interface MainView extends LoadingView, ErrorView, EmptyView, NetworkErrorView {

    Disposable subscribeInto(Flowable<MoviesResponse> flow);
}
