package ca.bhavik.assignment3.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ca.bhavik.assignment3.databinding.ActivityMovieDetailsBinding;
import ca.bhavik.assignment3.model.Movie;
import ca.bhavik.assignment3.repository.MovieRepository;
import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private MovieRepository repository;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new MovieRepository();
        movie = (Movie) getIntent().getSerializableExtra("MOVIE");

        // Populate fields
        binding.movieTitle.setText(movie.getTitle());
        binding.moviePlot.setText(movie.getPlot());
        if (movie.getPoster() != null && !movie.getPoster().equals("N/A")) {
            Glide.with(this)
                    .load(movie.getPoster())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.moviePoster);
        }

        binding.updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMovieActivity.class);
            intent.putExtra("MODE", "EDIT");
            intent.putExtra("MOVIE", movie);
            startActivity(intent);
        });

        binding.deleteButton.setOnClickListener(v -> {
            repository.deleteMovie(movie.getId());
            finish();
        });

        binding.backButton.setOnClickListener(v -> finish());
    }
}