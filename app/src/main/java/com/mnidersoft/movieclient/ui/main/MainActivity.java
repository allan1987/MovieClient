package com.mnidersoft.movieclient.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mnidersoft.movieclient.R;
import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.presentation.main.MainPresenter;
import com.mnidersoft.movieclient.presentation.main.MainView;
import com.mnidersoft.movieclient.ui.EndlessRecyclerScrollListener;
import com.mnidersoft.movieclient.ui.MoviesAdapter;
import com.mnidersoft.movieclient.ui.search.SearchActivity;

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

    private MenuItem mSearchItem;

    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle(R.string.main_activity_title);

        mAdapter = new MoviesAdapter(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mMoviesView.addOnScrollListener(new EndlessRecyclerScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.loadNowPlayingMovies(currentPage);
            }
        });

        mMoviesView.setLayoutManager(layoutManager);
        mMoviesView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(() -> {
            clear();
            loadMovies();
        });

        loadMovies();
    }

    private void loadMovies() {
        if (mPresenter != null) mPresenter.loadNowPlayingMovies();
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

    private void clear() {
        mAdapter.clear();
    }

    @Override
    public Disposable subscribeInto(Flowable<MoviesResponse> flow) {
        return flow
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        moviesResponse -> mAdapter.addAll(moviesResponse.getResults()),
                        throwable -> Log.e(TAG, "Error -> " + throwable.getMessage()),
                        () -> Log.i(TAG, "Done")
                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        mSearchItem = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
