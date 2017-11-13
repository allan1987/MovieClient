package com.mnidersoft.movieclient.core.modules;

import android.arch.lifecycle.LifecycleOwner;

import com.mnidersoft.movieclient.core.states.error.ErrorView;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorView;
import com.mnidersoft.movieclient.presentation.details.DetailsView;
import com.mnidersoft.movieclient.ui.details.DetailsActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

@Module(includes = {StatesModule.class, LifecycleStrategistModule.class,
        PresentationModule.class})
public class DetailsModule {

    @Provides
    static DetailsView detailsView(DetailsActivity activity) {
        return activity;
    }

    @Provides
    static LoadingView loadingView(DetailsActivity activity) {
        return activity;
    }

    @Provides
    static ErrorView errorView(DetailsActivity activity) {
        return activity;
    }

    @Provides
    static NetworkErrorView networkErrorView(DetailsActivity activity) {
        return activity;
    }

    @Provides
    static LifecycleOwner strategist(DetailsActivity activity) {
        return activity;
    }
}
