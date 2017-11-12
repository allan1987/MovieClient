package com.mnidersoft.movieclient.core.modules;

import com.mnidersoft.movieclient.core.qualifiers.SchedulerUI;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.core.states.empty.EmptyState;
import com.mnidersoft.movieclient.core.states.empty.EmptyView;
import com.mnidersoft.movieclient.core.states.error.ErrorState;
import com.mnidersoft.movieclient.core.states.error.ErrorView;
import com.mnidersoft.movieclient.core.states.loading.LoadingState;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorFeedback;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

@Module
public class StatesModule {

    @Provides
    static StatesCoordinator statesCoordinator(EmptyState emptyState, ErrorState errorState,
                                               LoadingState loadingState, NetworkErrorFeedback networkErrorFeedback) {

        return new StatesCoordinator(emptyState, errorState, loadingState, networkErrorFeedback);
    }

    @Provides
    static LoadingState loadingState(LoadingView view, @SchedulerUI Scheduler scheduler) {
        return new LoadingState(view, scheduler);
    }

    @Provides
    static ErrorState errorState(ErrorView view, @SchedulerUI Scheduler scheduler) {
        return new ErrorState(view, scheduler);
    }

    @Provides
    static EmptyState emptyState(EmptyView view, @SchedulerUI Scheduler scheduler) {
        return new EmptyState(view, scheduler);
    }

    @Provides
    static NetworkErrorFeedback networkErrorFeedback(NetworkErrorView view,
                                                     @SchedulerUI Scheduler scheduler) {
        return new NetworkErrorFeedback(view, scheduler);
    }
}