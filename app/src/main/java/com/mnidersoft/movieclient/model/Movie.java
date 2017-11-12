package com.mnidersoft.movieclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class Movie implements Serializable {

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("poster_path")
    private String posterPath;

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
