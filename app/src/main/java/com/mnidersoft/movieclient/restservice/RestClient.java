package com.mnidersoft.movieclient.restservice;

import com.mnidersoft.movieclient.core.errors.DeserializationHandler;
import com.mnidersoft.movieclient.core.errors.RestErrorsHandler;
import com.mnidersoft.movieclient.core.states.networkerror.NetworkErrorHandler;
import com.mnidersoft.movieclient.model.MoviesResponse;

import java.util.Locale;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class RestClient {

    MovieDBService mService;
    Scheduler mScheduler;
    String mLanguage;

    public RestClient(MovieDBService service, Scheduler ioScheduler) {
        mService = service;
        mScheduler = ioScheduler;

        Locale locale = Locale.getDefault();
        if (locale != null) mLanguage = locale.getLanguage();
    }

    public Flowable<MoviesResponse> loadNowPlayingMovies(int page) {
        return mService
                .loadNowPlayingMovies(mLanguage, page)
                .subscribeOn(mScheduler)
                .compose(new NetworkErrorHandler<>())
                .compose(new RestErrorsHandler<>())
                .compose(new DeserializationHandler<>());
    }

    public Flowable<MoviesResponse> searchMoviesByTitle(String query, int page) {
        return mService
                .searchMoviesByTitle(query, page)
                .subscribeOn(mScheduler)
                .compose(new NetworkErrorHandler<>())
                .compose(new RestErrorsHandler<>())
                .compose(new DeserializationHandler<>());
    }
}
