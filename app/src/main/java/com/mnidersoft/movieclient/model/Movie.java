package com.mnidersoft.movieclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class Movie implements Serializable {

    private Long id;

    @SerializedName("vote_average")
    private Float voteAverage;

    @SerializedName("poster_path")
    private String posterPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
