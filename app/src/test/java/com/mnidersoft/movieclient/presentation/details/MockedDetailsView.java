package com.mnidersoft.movieclient.presentation.details;

import com.mnidersoft.movieclient.model.MovieDetails;
import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.presentation.main.MainView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by allan on 15/11/17.
 */

public class MockedDetailsView implements DetailsView {

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

    private Consumer<MovieDetails> modelConsumer;
    private Consumer<Throwable> errorConsumer;
    private Action done;

    public MockedDetailsView(Consumer<MovieDetails> modelConsumer, Consumer<Throwable> errorConsumer,
                             Action done) {

        MockitoAnnotations.initMocks(this);
        this.modelConsumer = modelConsumer;
        this.errorConsumer = errorConsumer;
        this.done = done;
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
    public Disposable subscribeInto(Flowable<MovieDetails> flow) {
        return flow.subscribe(modelConsumer, errorConsumer, done);
    }
}
