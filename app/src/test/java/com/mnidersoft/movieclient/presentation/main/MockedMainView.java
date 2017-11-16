package com.mnidersoft.movieclient.presentation.main;

import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.viewmodel.MainViewModel;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by allan on 15/11/17.
 */

public class MockedMainView implements MainView {

    @Mock
    private Action showEmptyAction;

    @Mock
    private Action hideEmptyAction;

    @Mock
    private Action showErrorAction;

    @Mock
    private Action hideErrorAction;

    @Mock
    private Action showLoadingAction;

    @Mock
    private Action hideLoadingAction;

    @Mock
    private Action reportNetworkingErrorAction;

    private Consumer<MoviesResponse> modelConsumer;
    private Consumer<Throwable> errorConsumer;
    private Action done;

    public MockedMainView(Consumer<MoviesResponse> modelConsumer, Consumer<Throwable> errorConsumer,
                                Action done) {

        MockitoAnnotations.initMocks(this);
        this.modelConsumer = modelConsumer;
        this.errorConsumer = errorConsumer;
        this.done = done;
    }

    @Override
    public Action showEmpty() {
        return showEmptyAction;
    }

    @Override
    public Action hideEmpty() {
        return hideEmptyAction;
    }

    @Override
    public Action showError() {
        return showErrorAction;
    }

    @Override
    public Action hideError() {
        return hideErrorAction;
    }

    @Override
    public Action showLoading() {
        return showLoadingAction;
    }

    @Override
    public Action hideLoading() {
        return hideLoadingAction;
    }

    @Override
    public Action showNetworkError() {
        return reportNetworkingErrorAction;
    }

    @Override
    public Disposable subscribeInto(Flowable<MoviesResponse> flow) {
        return flow.subscribe(modelConsumer, errorConsumer, done);
    }
}
