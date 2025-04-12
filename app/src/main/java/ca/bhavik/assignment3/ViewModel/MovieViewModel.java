package ca.bhavik.assignment3.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ca.bhavik.assignment3.api.MovieApi;
import ca.bhavik.assignment3.api.RetrofitClient;
import ca.bhavik.assignment3.model.Movie;
import ca.bhavik.assignment3.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private static final String API_KEY = "e5bfd832";

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public void searchMovies(String query) {
        MovieApi movieApi = RetrofitClient.getClient("https://www.omdbapi.com/");
        Call<MovieResponse> call = movieApi.getMovies(API_KEY, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null && "True".equals(response.body().getResponse())) {
                    List<Movie> searchResults = response.body().getSearch();
                    fetchDetailedMovies(searchResults, movieApi);
                } else {
                    movieList.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movieList.setValue(new ArrayList<>());
            }
        });
    }

    private void fetchDetailedMovies(List<Movie> searchResults, MovieApi movieApi) {
        List<Movie> detailedMovies = new ArrayList<>();
        for (Movie movie : searchResults) {
            if (movie.getImdbID() != null && !movie.getImdbID().isEmpty()) {
                Call<Movie> detailCall = movieApi.getMovieDetails(API_KEY, movie.getImdbID());
                detailCall.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Movie detailedMovie = response.body();
                            detailedMovies.add(detailedMovie);
                            if (detailedMovies.size() == searchResults.size()) {
                                movieList.setValue(detailedMovies);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        detailedMovies.add(movie);
                        if (detailedMovies.size() == searchResults.size()) {
                            movieList.setValue(detailedMovies);
                        }
                    }
                });
            } else {
                detailedMovies.add(movie);
                if (detailedMovies.size() == searchResults.size()) {
                    movieList.setValue(detailedMovies);
                }
            }
        }
    }
}