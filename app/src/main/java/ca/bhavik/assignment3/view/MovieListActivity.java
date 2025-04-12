package ca.bhavik.assignment3.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import ca.bhavik.assignment3.adapter.MovieAdapter;
import ca.bhavik.assignment3.databinding.ActivityFavoritesBinding;
import ca.bhavik.assignment3.databinding.ActivitySearchBinding;
import ca.bhavik.assignment3.model.Movie;
import ca.bhavik.assignment3.ui.AddMovieActivity;
import ca.bhavik.assignment3.ui.FavoritesDetailsActivity;
import ca.bhavik.assignment3.ui.MovieDetailsActivity;
import ca.bhavik.assignment3.viewmodel.MovieViewModel;

public class MovieListActivity extends AppCompatActivity {
    private ActivitySearchBinding searchBinding;
    private ActivityFavoritesBinding favoritesBinding;
    private MovieViewModel viewModel;
    private boolean isSearchTab = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding for both layouts
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        favoritesBinding = ActivityFavoritesBinding.inflate(getLayoutInflater());

        // Set the initial view to the search tab
        setContentView(searchBinding.getRoot());

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Set up the Search and Favorites tabs
        setupSearchTab();
        setupFavoritesTab();

        // Set up tab switching buttons
        searchBinding.searchTabButton.setOnClickListener(v -> switchToSearchTab());
        searchBinding.favoritesTabButton.setOnClickListener(v -> switchToFavoritesTab());
        favoritesBinding.searchTabButton.setOnClickListener(v -> switchToSearchTab());
        favoritesBinding.favoritesTabButton.setOnClickListener(v -> switchToFavoritesTab());
    }

    /**
     * Sets up the Search tab functionality.
     * Handles movie search input and displays results in a RecyclerView.
     */
    private void setupSearchTab() {
        searchBinding.searchButton.setOnClickListener(v -> {
            String query = searchBinding.searchField.getText().toString().trim();
            if (!query.isEmpty()) {
                // Trigger movie search via ViewModel
                viewModel.searchMovies(query);
            }
        });

        // Observe search results and update RecyclerView
        viewModel.getSearchResults().observe(this, movies -> {
            MovieAdapter adapter = new MovieAdapter(movies, this, movie -> {
                // Navigate to MovieDetailsActivity when a movie is clicked
                Intent intent = new Intent(this, MovieDetailsActivity.class);
                intent.putExtra("MOVIE", movie);
                startActivity(intent);
            });
            searchBinding.recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Sets up the Favorites tab functionality.
     * Displays favorite movies from Firestore in a RecyclerView.
     */
    private void setupFavoritesTab() {
        // Observe favorite movies and update RecyclerView
        viewModel.getFavoriteMovies().observe(this, movies -> {
            MovieAdapter adapter = new MovieAdapter(movies, this, movie -> {
                // Navigate to FavoritesDetailsActivity when a movie is clicked
                Intent intent = new Intent(this, FavoritesDetailsActivity.class);
                intent.putExtra("MOVIE", movie);
                startActivity(intent);
            });
            favoritesBinding.recyclerView.setAdapter(adapter);
        });

        // Optional: Uncomment to enable adding a new movie via FAB
        /*
        favoritesBinding.addMovieButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMovieActivity.class);
            intent.putExtra("MODE", "ADD");
            startActivity(intent);
        });
        */
    }

    /**
     * Switches the UI to the Search tab.
     */
    private void switchToSearchTab() {
        if (!isSearchTab) {
            setContentView(searchBinding.getRoot());
            isSearchTab = true;
        }
    }

    /**
     * Switches the UI to the Favorites tab.
     */
    private void switchToFavoritesTab() {
        if (isSearchTab) {
            setContentView(favoritesBinding.getRoot());
            isSearchTab = false;
        }
    }
}