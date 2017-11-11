package com.mnidersoft.movieclient.core.modules;

import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.presentation.MainPresenter;
import com.mnidersoft.movieclient.presentation.MainView;
import com.mnidersoft.movieclient.restservice.RestClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

@Module
public class PresentationModule {

    @Provides
    static MainPresenter presenter(RestClient restClient, MainView view,
                                   StatesCoordinator coordinator, LifecycleStrategist strategist) {

        return new MainPresenter(restClient, view, coordinator, strategist);
    }
}