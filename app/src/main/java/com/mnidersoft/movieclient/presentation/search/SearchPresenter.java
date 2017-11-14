package com.mnidersoft.movieclient.presentation.search;

import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;
import com.mnidersoft.movieclient.core.qualifiers.DefaultStates;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.restservice.RestClient;
import com.mnidersoft.movieclient.util.AppUtil;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public class SearchPresenter {

    private RestClient mRestClient;
    private SearchView mView;
    private StatesCoordinator<MoviesResponse> mCoordinator;
    private LifecycleStrategist mStrategist;
    private int mPage;

    public SearchPresenter(RestClient restClient, SearchView view,
                           @DefaultStates StatesCoordinator<MoviesResponse> coordinator,
                           LifecycleStrategist strategist) {
        mRestClient = restClient;
        mView = view;
        mCoordinator = coordinator;
        mStrategist = strategist;
    }

    public void searchMoviesByTitle(String query, int page) {
        if (AppUtil.isNullOrEmpty(query)) return;
        mPage = page;

        Flowable<MoviesResponse> dataFlow = mRestClient.searchMoviesByTitle(query, mPage).compose(mCoordinator);

        Disposable toDispose = mView.subscribeInto(dataFlow);
        mStrategist.applyStrategy(toDispose);
    }
}