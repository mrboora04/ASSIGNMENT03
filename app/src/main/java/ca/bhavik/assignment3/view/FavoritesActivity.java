package ca.bhavik.assignment3.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import ca.bhavik.assignment3.adapter.MovieAdapter; // Updated package
import ca.bhavik.assignment3.databinding.ActivityFavoritesBinding;
import ca.bhavik.assignment3.model.Movie;
import ca.bhavik.assignment3.ui.FavoritesDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private ActivityFavoritesBinding binding;
    private MovieAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        adapter = new MovieAdapter(new ArrayList<>(), this, movie -> {
            Intent intent = new Intent(this, FavoritesDetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        loadFavorites();

        binding.searchTabButton.setOnClickListener(v -> {
            startActivity(new Intent(this, MovieListActivity.class));
            finish();
        });
        binding.favoritesTabButton.setEnabled(false);
    }

    private void loadFavorites() {
        if (auth.getCurrentUser() == null) return;

        db.collection("users")
                .document(auth.getCurrentUser().getUid())
                .collection("favorites")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Movie> favorites = new ArrayList<>();
                    for (var doc : queryDocumentSnapshots) {
                        Movie movie = doc.toObject(Movie.class);
                        favorites.add(movie);
                    }
                    adapter = new MovieAdapter(favorites, this, movie1 -> {
                        Intent intent = new Intent(this, FavoritesDetailsActivity.class);
                        intent.putExtra("movie", movie1);
                        startActivity(intent);
                    });
                    binding.recyclerView.setAdapter(adapter);
                });
    }
}