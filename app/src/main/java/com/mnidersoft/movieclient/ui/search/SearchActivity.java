package com.mnidersoft.movieclient.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mnidersoft.movieclient.R;
import com.mnidersoft.movieclient.model.MoviesResponse;
import com.mnidersoft.movieclient.presentation.search.SearchPresenter;
import com.mnidersoft.movieclient.presentation.search.SearchView;
import com.mnidersoft.movieclient.ui.EndlessRecyclerScrollListener;
import com.mnidersoft.movieclient.ui.MoviesAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public class SearchActivity extends AppCompatActivity implements SearchView {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.search_edit_text)
    protected EditText mSearchEditText;

    @BindView(R.id.recyclerview_movies)
    protected RecyclerView mMoviesView;

    @BindView(R.id.container)
    protected View mContainer;

    @BindView(R.id.error_message)
    protected TextView mErrorMessage;

    @BindView(R.id.progress_bar)
    protected ProgressBar mLoading;

    @Inject
    protected SearchPresenter mPresenter;

    private MoviesAdapter mAdapter;

    private String currentQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mAdapter = new MoviesAdapter(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mMoviesView.addOnScrollListener(new EndlessRecyclerScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.searchMoviesByTitle(currentQuery, currentPage);
            }
        });

        mMoviesView.setLayoutManager(layoutManager);
        mMoviesView.setAdapter(mAdapter);

        mSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                currentQuery = v.getText().toString();

                InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (in != null) in.hideSoftInputFromWindow(v.getWindowToken(), 0);

                clear();
                searchMoviesByTitle();
                return true;
            }
            return false;
        });
    }

    private void searchMoviesByTitle() {
        if (mPresenter != null) mPresenter.searchMoviesByTitle(currentQuery);
    }

    @Override
    public Action showLoading() {
        return () -> {
            mLoading.setVisibility(View.VISIBLE);
        };
    }

    @Override
    public Action hideLoading() {
        return () -> {
            mLoading.setVisibility(View.GONE);
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
}
