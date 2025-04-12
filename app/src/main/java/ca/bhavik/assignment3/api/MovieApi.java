package ca.bhavik.assignment3.api;

import ca.bhavik.assignment3.model.Movie;
import ca.bhavik.assignment3.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("/")
    Call<MovieResponse> getMovies(@Query("apikey") String apiKey, @Query("s") String searchQuery);

    @GET("/")
    Call<Movie> getMovieDetails(@Query("apikey") String apiKey, @Query("i") String imdbID);
}