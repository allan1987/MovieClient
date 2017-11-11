package com.mnidersoft.movieclient.restservice;

import com.mnidersoft.movieclient.core.errors.DeserializationHandler;
import com.mnidersoft.movieclient.core.errors.RestErrorsHandler;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorHandler;
import com.mnidersoft.movieclient.model.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class RestClient {

    MovieDBService mService;
    Scheduler mScheduler;

    public RestClient(MovieDBService service, Scheduler ioScheduler) {
        mService = service;
        mScheduler = ioScheduler;
    }

    public Flowable<List<Movie>> loadMovies() {
        return mService
                .loadMovies()
                .subscribeOn(mScheduler)
                .compose(new NetworkErrorHandler<>())
                .compose(new RestErrorsHandler<>())
                .compose(new DeserializationHandler<>());
    }
}
