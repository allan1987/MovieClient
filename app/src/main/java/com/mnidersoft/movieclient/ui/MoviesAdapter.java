package com.mnidersoft.movieclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mnidersoft.movieclient.R;
import com.mnidersoft.movieclient.model.Movie;
import com.mnidersoft.movieclient.ui.details.DetailsActivity;
import com.mnidersoft.movieclient.util.AppUtil;
import com.mnidersoft.movieclient.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> mMovies = new ArrayList<>();

    private Activity mActivity;

    public MoviesAdapter(Activity activity) {
        mActivity = activity;
    }

    public void add(Movie movie) {
        int pos = mMovies.size();
        mMovies.add(movie);
        notifyItemInserted(pos);
    }

    public void addAll(List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(mActivity)
                .inflate(R.layout.view_movie_item, parent, false);
        return new MovieHolder(card);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        MovieHolder viewHolder = (MovieHolder) holder;
        GlideUtil.loadImage(mActivity, movie.getPosterPath(), viewHolder.posterImage);
        viewHolder.ratingBar.setRating(movie.getVoteAverage() / 2);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_MOVIE_ID, movie.getId());
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return !AppUtil.isNullOrEmpty(mMovies) ? mMovies.size() : 0;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_item_poster_image)
        public ImageView posterImage;

        @BindView(R.id.movie_item_rating_bar)
        public AppCompatRatingBar ratingBar;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public List<Movie> getItems() {
        return mMovies;
    }
}
