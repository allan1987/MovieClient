package com.mnidersoft.movieclient.core.modules;

import android.arch.lifecycle.LifecycleOwner;

import com.mnidersoft.movieclient.core.states.empty.EmptyView;
import com.mnidersoft.movieclient.core.states.error.ErrorView;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorView;
import com.mnidersoft.movieclient.presentation.MainView;
import com.mnidersoft.movieclient.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

@Module(includes = {StatesModule.class, LifecycleStrategistModule.class,
        PresentationModule.class})
public class MainModule {

    @Provides
    static MainView mainView(MainActivity activity) {
        return activity;
    }

    @Provides
    static LoadingView loadingView(MainActivity activity) {
        return activity;
    }

    @Provides
    static ErrorView errorView(MainActivity activity) {
        return activity;
    }

    @Provides
    static EmptyView emptyView(MainActivity activity) {
        return activity;
    }

    @Provides
    static NetworkErrorView networkErrorView(MainActivity activity) {
        return activity;
    }

    @Provides
    static LifecycleOwner strategist(MainActivity activity) {
        return activity;
    }
}
