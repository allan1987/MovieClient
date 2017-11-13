package com.mnidersoft.movieclient.ui.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mnidersoft.movieclient.R;
import com.mnidersoft.movieclient.model.MovieDetails;
import com.mnidersoft.movieclient.presentation.details.DetailsPresenter;
import com.mnidersoft.movieclient.presentation.details.DetailsView;

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

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    public static final String EXTRA_MOVIE_ID = "movie_id";

    @BindView(R.id.error_message)
    protected TextView mErrorMessage;

    @BindView(R.id.progress_bar)
    protected ProgressBar mLoading;

    @Inject
    protected DetailsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle(R.string.details_activity_title);

        if (getIntent().hasExtra(EXTRA_MOVIE_ID)) {
            getMovieDetails(getIntent().getLongExtra(EXTRA_MOVIE_ID, 0));
        }
    }

    private void getMovieDetails(Long id) {
        if (mPresenter != null) mPresenter.getMovieDetails(id);
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
    public Disposable subscribeInto(Flowable<MovieDetails> flow) {
        return flow
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::fillMovieDetails,
                        throwable -> Log.e(TAG, "Error -> " + throwable.getMessage()),
                        () -> Log.i(TAG, "Done")
                );
    }

    private void fillMovieDetails(MovieDetails movieDetails) {
        movieDetails.getBackdropPath();
    }
}
