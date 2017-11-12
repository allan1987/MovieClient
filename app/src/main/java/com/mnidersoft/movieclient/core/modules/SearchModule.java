package com.mnidersoft.movieclient.core.modules;

import android.arch.lifecycle.LifecycleOwner;

import com.mnidersoft.movieclient.core.states.empty.EmptyView;
import com.mnidersoft.movieclient.core.states.error.ErrorView;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorView;
import com.mnidersoft.movieclient.presentation.search.SearchView;
import com.mnidersoft.movieclient.ui.search.SearchActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

@Module(includes = {StatesModule.class, LifecycleStrategistModule.class,
        PresentationModule.class})
public class SearchModule {

    @Provides
    static SearchView searchView(SearchActivity activity) {
        return activity;
    }

    @Provides
    static LoadingView loadingView(SearchActivity activity) {
        return activity;
    }

    @Provides
    static ErrorView errorView(SearchActivity activity) {
        return activity;
    }

    @Provides
    static EmptyView emptyView(SearchActivity activity) {
        return activity;
    }

    @Provides
    static NetworkErrorView networkErrorView(SearchActivity activity) {
        return activity;
    }

    @Provides
    static LifecycleOwner strategist(SearchActivity activity) {
        return activity;
    }
}
