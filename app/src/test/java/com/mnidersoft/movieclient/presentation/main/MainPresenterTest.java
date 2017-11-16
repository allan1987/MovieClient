package com.mnidersoft.movieclient.presentation.main;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mnidersoft.movieclient.core.errors.ContentNotFoundError;
import com.mnidersoft.movieclient.core.errors.NetworkError;
import com.mnidersoft.movieclient.core.errors.UnexpectedResponseError;
import com.mnidersoft.movieclient.core.lifecycles.LifecycleStrategist;
import com.mnidersoft.movieclient.core.states.StatesCoordinator;
import com.mnidersoft.movieclient.core.states.empty.EmptyState;
import com.mnidersoft.movieclient.core.states.error.ErrorState;
import com.mnidersoft.movieclient.core.states.loading.LoadingState;
import com.mnidersoft.movieclient.core.states.loading.LoadingView;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorFeedback;
import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.restservice.RestClient;
import com.mnidersoft.movieclient.util.AppUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by allan on 15/11/17.
 */

public class MainPresenterTest {

    private MainView mView;
    private MainPresenter mPresenter;

    @Mock
    private RestClient mRestClient;

    @Mock
    private LifecycleStrategist mStrategist;

    @Mock
    private Consumer<MoviesResponse> mOnNext;

    @Mock
    private Consumer<Throwable> mOnError;

    @Mock
    private Action mOnCompleted;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Scheduler uiScheduler = Schedulers.trampoline();

        mView = new MockedMainView(mOnNext, mOnError, mOnCompleted);

        StatesCoordinator<MoviesResponse> coordinator = new StatesCoordinator<>(
                new EmptyState<>(mView, uiScheduler),
                new ErrorState<>(mView, uiScheduler),
                new LoadingState<>(mView, uiScheduler),
                new NetworkErrorFeedback<>(mView, uiScheduler)
        );

        mPresenter = new MainPresenter(mRestClient, mView, coordinator, mStrategist);
    }

    @Test
    public void testWithAvailableData() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(AppUtil.getBasePath() + "test/resources/moviesResponse.json"));
        MoviesResponse moviesResponse = new Gson().fromJson(br, MoviesResponse.class);
        Flowable<MoviesResponse> data = Flowable.just(moviesResponse);

        Mockito.when(mRestClient.loadNowPlayingMovies(1)).thenReturn(data);

        mPresenter.loadMovies(1);

        InOrder inOrder = Mockito.inOrder(mView.showLoading(), mView.hideLoading());
        inOrder.verify(mView.showLoading(), Mockito.times(1)).run();
        inOrder.verify(mView.hideLoading(), Mockito.times(1)).run();

        Mockito.verify(mView.showEmpty(), Mockito.never()).run();

        Mockito.verify(mView.showError(), Mockito.never()).run();

        Mockito.verify(mView.showNetworkError(), Mockito.never()).run();

        Mockito.verify(mOnNext).accept(Matchers.any());
        Mockito.verify(mOnCompleted).run();
        Mockito.verifyZeroInteractions(mOnError);

        Mockito.verify(mStrategist, Mockito.times(1)).applyStrategy(Matchers.any());
    }

    @Test
    public void testWithoutContentData() throws Exception {
        Flowable<MoviesResponse> noContent = Flowable.error(new ContentNotFoundError());
        Mockito.when(mRestClient.loadNowPlayingMovies(1)).thenReturn(noContent);
        mPresenter.loadMovies(1);

        InOrder inOrder = Mockito.inOrder(mView.showLoading(), mView.hideLoading());
        inOrder.verify(mView.showLoading(), Mockito.times(1)).run();
        inOrder.verify(mView.hideLoading(), Mockito.times(1)).run();

        Mockito.verify(mView.showEmpty(), Mockito.times(1)).run();

        Mockito.verify(mView.showError(), Mockito.never()).run();

        Mockito.verify(mView.showNetworkError(), Mockito.never()).run();

        Mockito.verify(mOnError, Mockito.times(1)).accept(Matchers.any());

        Mockito.verifyZeroInteractions(mOnNext);

        Mockito.verifyZeroInteractions(mOnError);

        Mockito.verify(mStrategist, Mockito.times(1)).applyStrategy(Matchers.any());
    }

    @Test
    public void testWithUnexpectedError() throws Exception {
        Flowable<MoviesResponse> error = Flowable.error(new UnexpectedResponseError("503"));
        Mockito.when(mRestClient.loadNowPlayingMovies(1)).thenReturn(error);
        mPresenter.loadMovies(1);

        InOrder inOrder = Mockito.inOrder(mView.showLoading(), mView.hideLoading());
        inOrder.verify(mView.showLoading(), Mockito.times(1)).run();
        inOrder.verify(mView.hideLoading(), Mockito.times(1)).run();

        Mockito.verify(mView.showEmpty(), Mockito.never()).run();

        Mockito.verify(mView.showError(), Mockito.times(1)).run();

        Mockito.verify(mView.showNetworkError(), Mockito.never()).run();

        Mockito.verify(mOnError, Mockito.times(1)).accept(Matchers.any());

        Mockito.verifyZeroInteractions(mOnNext);

        Mockito.verifyZeroInteractions(mOnError);

        Mockito.verify(mStrategist, Mockito.times(1)).applyStrategy(Matchers.any());
    }

    @Test
    public void testWithOtherError() throws Exception {
        Flowable<MoviesResponse> broken = Flowable.error(new IllegalAccessError("Illegal"));
        Mockito.when(mRestClient.loadNowPlayingMovies(1)).thenReturn(broken);

        mPresenter.loadMovies(1);

        InOrder inOrder = Mockito.inOrder(mView.showLoading(), mView.hideLoading());
        inOrder.verify(mView.showLoading(), Mockito.times(1)).run();
        inOrder.verify(mView.hideLoading(), Mockito.times(1)).run();

        Mockito.verify(mView.showEmpty(), Mockito.never()).run();

        Mockito.verify(mView.showError(), Mockito.times(1)).run();

        Mockito.verify(mView.showNetworkError(), Mockito.never()).run();

        Mockito.verify(mOnError, Mockito.times(1)).accept(Matchers.any());

        Mockito.verifyZeroInteractions(mOnNext);

        Mockito.verifyZeroInteractions(mOnError);

        Mockito.verify(mStrategist, Mockito.times(1)).applyStrategy(Matchers.any());
    }

    @Test
    public void testWithNetworkError() throws Exception {
        Flowable<MoviesResponse> error = Flowable.error(new NetworkError("Timeout"));
        Mockito.when(mRestClient.loadNowPlayingMovies(1)).thenReturn(error);

        mPresenter.loadMovies(1);

        InOrder inOrder = Mockito.inOrder(mView.showLoading(), mView.hideLoading());
        inOrder.verify(mView.showLoading(), Mockito.times(1)).run();
        inOrder.verify(mView.hideLoading(), Mockito.times(1)).run();

        Mockito.verify(mView.showEmpty(), Mockito.never()).run();

        Mockito.verify(mView.showError(), Mockito.times(1)).run();

        Mockito.verify(mView.showNetworkError(), Mockito.times(1)).run();

        Mockito.verify(mOnError, Mockito.times(1)).accept(Matchers.any());

        Mockito.verifyZeroInteractions(mOnNext);

        Mockito.verifyZeroInteractions(mOnError);

        Mockito.verify(mStrategist, Mockito.times(1)).applyStrategy(Matchers.any());
    }
}
