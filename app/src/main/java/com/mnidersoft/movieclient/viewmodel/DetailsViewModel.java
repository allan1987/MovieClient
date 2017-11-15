package com.mnidersoft.movieclient.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mnidersoft.movieclient.model.Movie;
import com.mnidersoft.movieclient.model.MovieDetails;
import com.mnidersoft.movieclient.presentation.details.DetailsPresenter;
import com.mnidersoft.movieclient.presentation.search.SearchPresenter;
import com.mnidersoft.movieclient.util.AppUtil;

import java.util.List;

/**
 * Created by allan on 14/11/17.
 */

public class DetailsViewModel extends ViewModel {

    private MutableLiveData<MovieDetails> mDetails = new MutableLiveData<>();

    private DetailsPresenter mPresenter;

    public void setPresenter(DetailsPresenter presenter) {
        mPresenter = presenter;
    }

    public LiveData<MovieDetails> loadMovieDetails(Long id) {
        if (mPresenter != null) mPresenter.getMovieDetails(id);
        return mDetails;
    }

    public MovieDetails getMovieDetails() {
        return mDetails.getValue();
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        mDetails.setValue(movieDetails);
    }

    public boolean hasMovieDetails() {
        return mDetails.getValue() != null;
    }
}
