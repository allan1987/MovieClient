package com.mnidersoft.movieclient.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Allan.Menezes on 11/11/17.
 */

public class MoviesResponse implements Serializable {

    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
