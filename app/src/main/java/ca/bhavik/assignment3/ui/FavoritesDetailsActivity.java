package ca.bhavik.assignment3.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ca.bhavik.assignment3.databinding.ActivityFavoritesDetailsBinding;
import ca.bhavik.assignment3.model.Movie;
import ca.bhavik.assignment3.repository.MovieRepository;
import com.bumptech.glide.Glide;

public class FavoritesDetailsActivity extends AppCompatActivity {
    private ActivityFavoritesDetailsBinding binding;
    private MovieRepository repository;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new MovieRepository();
        movie = (Movie) getIntent().getSerializableExtra("MOVIE");

        // Populate fields
        binding.movieTitle.setText(movie.getTitle());
        binding.movieYear.setText(movie.getYear());
        binding.movieRating.setText("IMDb: " + movie.getImdbRating());
        binding.moviePlot.setText(movie.getPlot());
        if (movie.getPoster() != null && !movie.getPoster().equals("N/A")) {
            Glide.with(this)
                    .load(movie.getPoster())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.moviePoster);
        }

        binding.addToFavButton.setOnClickListener(v -> {
            // Already in favorites, maybe show a message
        });

        binding.backButton.setOnClickListener(v -> finish());
    }
}