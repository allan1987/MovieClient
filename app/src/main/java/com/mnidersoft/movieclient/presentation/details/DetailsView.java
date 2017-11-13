package com.mnidersoft.movieclient.presentation.details;

import com.mnidersoft.movieclient.core.states.error.ErrorView;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorView;
import com.mnidersoft.movieclient.model.MovieDetails;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public interface DetailsView extends LoadingView, ErrorView, NetworkErrorView {

    Disposable subscribeInto(Flowable<MovieDetails> flow);
}
