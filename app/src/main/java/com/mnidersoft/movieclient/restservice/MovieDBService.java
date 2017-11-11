package com.mnidersoft.movieclient.restservice;

import com.mnidersoft.movieclient.model.Movie;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

public interface MovieDBService {

    String BASE_URL = "https://api.themoviedb.org/4/";

    @GET()
    Flowable<List<Movie>> loadMovies();
}
