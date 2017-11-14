package com.mnidersoft.movieclient.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mnidersoft.movieclient.model.Movie;
import com.mnidersoft.movieclient.presentation.search.SearchPresenter;
import com.mnidersoft.movieclient.util.AppUtil;

import java.util.List;

/**
 * Created by allan on 14/11/17.
 */

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();
    private MutableLiveData<String> mQuery = new MutableLiveData<>();

    private SearchPresenter mPresenter;

    public void setPresenter(SearchPresenter presenter) {
        mPresenter = presenter;
    }

    public LiveData<List<Movie>> loadMovies(String query) {
        return loadMovies(query,1);
    }

    public LiveData<List<Movie>> loadMovies(String query, int currentPage) {
        if (mPresenter != null) mPresenter.searchMoviesByTitle(query, currentPage);
        return mMovies;
    }

    public List<Movie> getMovies() {
        return mMovies.getValue();
    }

    public void setMovies(List<Movie> movies) {
        mMovies.setValue(movies);
    }

    public boolean hasMovies() {
        return !AppUtil.isNullOrEmpty(mMovies.getValue());
    }

    public String getQuery() {
        return mQuery.getValue();
    }

    public void setQuery(String query) {
        mQuery.setValue(query);
    }
}
