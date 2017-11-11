package com.mnidersoft.movieclient.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mnidersoft.movieclient.R;
import com.mnidersoft.movieclient.model.Movie;
import com.mnidersoft.movieclient.presentation.MainPresenter;
import com.mnidersoft.movieclient.presentation.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.recyclerview_movies)
    protected RecyclerView mMoviesView;

    @BindView(R.id.container)
    protected View mContainer;

    @BindView(R.id.error_message)
    protected TextView mErrorMessage;

    @BindView(R.id.progress_bar)
    protected ProgressBar mLoading;

    @Inject
    protected MainPresenter mPresenter;

    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies();
    }

    @Override
    public Action showLoading() {
        return () -> {
            if (!mSwipeRefresh.isRefreshing()) mLoading.setVisibility(View.VISIBLE);
        };
    }

    @Override
    public Action hideLoading() {
        return () -> {
            mLoading.setVisibility(View.GONE);
            mSwipeRefresh.setRefreshing(false);
        };
    }

    @Override
    public Action showEmpty() {
        return () -> {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText(R.string.empty_message);
        };
    }

    @Override
    public Action hideEmpty() {
        return () -> mErrorMessage.setVisibility(View.GONE);
    }

    @Override
    public Action showError() {
        return () -> {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText(R.string.default_error_message);
        };
    }

    @Override
    public Action hideError() {
        return () -> mErrorMessage.setVisibility(View.GONE);
    }

    @Override
    public Action showNetworkError() {
        return () -> {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText(R.string.network_error_message);
        };
    }

    @Override
    public Disposable subscribeInto(Flowable<List<Movie>> flow) {
        mAdapter.clear();
        return flow
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        models -> mAdapter.addModel(models),
                        throwable -> Log.e(TAG, "Error -> " + throwable.getMessage()),
                        () -> Log.i(TAG, "Done")
                );
    }

    private void init() {
        mAdapter = new MoviesAdapter(this);
        mMoviesView.setLayoutManager(new LinearLayoutManager(this));
        mMoviesView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener( () -> {
                mAdapter.clear();
                loadMovies();
        });
    }

    private void loadMovies() {
        if (mPresenter != null) mPresenter.loadMovies();
    }
}
