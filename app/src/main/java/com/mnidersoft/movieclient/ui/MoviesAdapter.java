package com.mnidersoft.movieclient.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnidersoft.movieclient.R;
import com.mnidersoft.movieclient.model.Movie;
import com.mnidersoft.movieclient.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> mMovies = new ArrayList<>();

    private Context mContext;

    public MoviesAdapter(Context context) {
        mContext = context;
    }

    public void addModel(Movie movie) {
        int pos = mMovies.size();
        mMovies.add(movie);
        notifyItemInserted(pos);
    }

    public void addModel(List<Movie> movies) {
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
        MovieHolder mh = (MovieHolder) holder;
//        vh.labelNumber.setText(movie.relatedNumber());
//        vh.labelFact.setText(movie.formattedFact());
    }

    @Override
    public int getItemCount() {
        return !AppUtil.isNullOrEmpty(mMovies) ? mMovies.size() : 0;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.label_number)
//        public TextView labelNumber;
//
//        @BindView(R.id.label_fact)
//        public TextView labelFact;

        public MovieHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }
}
