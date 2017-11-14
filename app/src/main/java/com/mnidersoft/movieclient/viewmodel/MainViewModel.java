package com.mnidersoft.movieclient.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mnidersoft.movieclient.model.Movie;
import com.mnidersoft.movieclient.presentation.main.MainPresenter;
import com.mnidersoft.movieclient.util.AppUtil;

import java.util.List;

/**
 * Created by allan on 14/11/17.
 */

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();

    private MainPresenter mPresenter;

    public void setPresenter(MainPresenter presenter) {
        mPresenter = presenter;
    }

    public LiveData<List<Movie>> loadMovies() {
        return loadMovies(1);
    }

    public LiveData<List<Movie>> loadMovies(int currentPage) {
        if (mPresenter != null) mPresenter.loadMovies(currentPage);
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
}
