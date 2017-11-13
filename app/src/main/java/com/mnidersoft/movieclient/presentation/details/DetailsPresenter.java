package com.mnidersoft.movieclient.presentation.details;

import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;
import com.mnidersoft.movieclient.core.qualifiers.DetailsStates;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.model.MovieDetails;
import com.mnidersoft.movieclient.restservice.RestClient;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public class DetailsPresenter {

    private RestClient mRestClient;
    private DetailsView mView;
    private StatesCoordinator<MovieDetails> mCoordinator;
    private LifecycleStrategist mStrategist;

    public DetailsPresenter(RestClient restClient, DetailsView view,
                            @DetailsStates StatesCoordinator<MovieDetails> coordinator,
                            LifecycleStrategist strategist) {
        mRestClient = restClient;
        mView = view;
        mCoordinator = coordinator;
        mStrategist = strategist;
    }

    public void getMovieDetails(Long id) {
        Flowable<MovieDetails> dataFlow = mRestClient.getMovieDetails(id).compose(mCoordinator);

        Disposable toDispose = mView.subscribeInto(dataFlow);
        mStrategist.applyStrategy(toDispose);
    }
}