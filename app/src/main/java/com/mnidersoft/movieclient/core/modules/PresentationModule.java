package com.mnidersoft.movieclient.core.modules;

import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;
import com.mnidersoft.movieclient.core.qualifiers.DefaultStates;
import com.mnidersoft.movieclient.core.qualifiers.DetailsStates;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.model.MovieDetails;
import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.presentation.details.DetailsPresenter;
import com.mnidersoft.movieclient.presentation.details.DetailsView;
import com.mnidersoft.movieclient.presentation.main.MainPresenter;
import com.mnidersoft.movieclient.presentation.main.MainView;
import com.mnidersoft.movieclient.presentation.search.SearchPresenter;
import com.mnidersoft.movieclient.presentation.search.SearchView;
import com.mnidersoft.movieclient.restservice.RestClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

@Module
public class PresentationModule {

    @Provides
    static MainPresenter mainPresenter(RestClient restClient, MainView view,
                                       @DefaultStates StatesCoordinator<MoviesResponse> coordinator,
                                       LifecycleStrategist strategist) {
        return new MainPresenter(restClient, view, coordinator, strategist);
    }

    @Provides
    static SearchPresenter searchPresenter(RestClient restClient, SearchView view,
                                           @DefaultStates StatesCoordinator<MoviesResponse> coordinator,
                                           LifecycleStrategist strategist) {
        return new SearchPresenter(restClient, view, coordinator, strategist);
    }

    @Provides
    static DetailsPresenter detailsPresenter(RestClient restClient, DetailsView view,
                                             @DetailsStates StatesCoordinator<MovieDetails> coordinator,
                                             LifecycleStrategist strategist) {
        return new DetailsPresenter(restClient, view, coordinator, strategist);
    }
}