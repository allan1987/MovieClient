package com.mnidersoft.movieclient.presentation.search;

import com.mnidersoft.movieclient.core.states.empty.EmptyView;
import com.mnidersoft.movieclient.core.states.error.ErrorView;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorView;
import com.mnidersoft.movieclient.model.MoviesResponse;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public interface SearchView extends LoadingView, ErrorView, EmptyView, NetworkErrorView {

    Disposable subscribeInto(Flowable<MoviesResponse> flow);
}
