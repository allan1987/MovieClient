package com.mnidersoft.movieclient.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mnidersoft.movieclient.R;
import com.mnidersoft.movieclient.model.Movie;
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

    private Context mContext;

    public MoviesAdapter(Context context) {
        mContext = context;
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
        View card = LayoutInflater.from(mContext).inflate(R.layout.view_movie_item, parent, false);
        return new MovieHolder(card);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        MovieHolder viewHolder = (MovieHolder) holder;
        GlideUtil.loadImage(mContext, movie.getPosterPath(), viewHolder.posterImage);
        viewHolder.ratingBar.setRating(movie.getVoteAverage() / 2);
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
}
