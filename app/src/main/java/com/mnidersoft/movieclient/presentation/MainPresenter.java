package com.mnidersoft.movieclient.presentation;

import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.restservice.RestClient;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class MainPresenter {

    private RestClient mRestClient;
    private MainView mView;
    private StatesCoordinator<MoviesResponse> mCoordinator;
    private LifecycleStrategist mStrategist;
    private int mPage;

    public MainPresenter(RestClient restClient, MainView view,
                         StatesCoordinator<MoviesResponse> coordinator,
                         LifecycleStrategist strategist) {
        mRestClient = restClient;
        mView = view;
        mCoordinator = coordinator;
        mStrategist = strategist;
    }

    public void loadMovies() {
        loadMovies(1);
    }

    public void loadMovies(int page) {
        mPage = page;

        Flowable<MoviesResponse> dataFlow = mRestClient.loadMovies(mPage).compose(mCoordinator);

        Disposable toDispose = mView.subscribeInto(dataFlow);
        mStrategist.applyStrategy(toDispose);
    }
}