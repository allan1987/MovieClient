package com.mnidersoft.movieclient.restservice;

import com.mnidersoft.movieclient.model.MoviesResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Allan.Menezes on 11/10/17.
 */

public interface MovieDBService {

    String BASE_URL = "https://api.themoviedb.org/4/";

    String API_KEY = "1b967111e1e661560ed39ab7cc61230d";

    @GET("discover/movie?vote_average.gte=5&with_release_type=2|3&api_key=" + API_KEY)
    Flowable<MoviesResponse> loadNowPlayingMovies(@Query("language") String language, @Query("page") int page);
}
