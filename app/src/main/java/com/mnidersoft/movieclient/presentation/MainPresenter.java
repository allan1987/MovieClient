package com.mnidersoft.movieclient.presentation;

import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.model.Movie;
import com.mnidersoft.movieclient.restservice.RestClient;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class MainPresenter {

    private RestClient mRestClient;
    private MainView mView;
    private StatesCoordinator<List<Movie>> mCoordinator;
    private LifecycleStrategist mStrategist;

    public MainPresenter(RestClient restClient, MainView view,
                         StatesCoordinator<List<Movie>> coordinator,
                         LifecycleStrategist strategist) {

        mRestClient = restClient;
        mView = view;
        mCoordinator = coordinator;
        mStrategist = strategist;
    }

    public void loadMovies() {
        Flowable<List<Movie>> dataFlow = mRestClient.loadMovies().compose(mCoordinator);

        Disposable toDispose = mView.subscribeInto(dataFlow);
        mStrategist.applyStrategy(toDispose);
    }
}
