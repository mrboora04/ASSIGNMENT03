package ca.bhavik.assignment3.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ca.bhavik.assignment3.databinding.ActivityAddEditMovieBinding;
import ca.bhavik.assignment3.model.Movie;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEditMovieActivity extends AppCompatActivity {
    private ActivityAddEditMovieBinding binding;
    private boolean isEdit = false;
    private String movieId;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            movieId = getIntent().getStringExtra("movieId");
            binding.txtTitle.setText(getIntent().getStringExtra("title"));
            binding.txtStudio.setText(getIntent().getStringExtra("studio"));
            binding.txtImageUrl.setText(getIntent().getStringExtra("imageUrl"));
            binding.txtRating.setText(String.valueOf(getIntent().getFloatExtra("rating", 0)));
            binding.txtHeader.setText("Edit Movie");
            binding.btnSubmit.setText("Update Movie");
        } else {
            binding.txtHeader.setText("Add Movie");
            binding.btnSubmit.setText("Add Movie");
        }

        binding.btnSubmit.setOnClickListener(v -> saveMovie());
        binding.btnCancel.setOnClickListener(v -> finish());
    }

    private void saveMovie() {
        String title = binding.txtTitle.getText().toString().trim();
        String studio = binding.txtStudio.getText().toString().trim();
        String imageUrl = binding.txtImageUrl.getText().toString().trim();
        String ratingStr = binding.txtRating.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(studio) ||
                TextUtils.isEmpty(imageUrl) || TextUtils.isEmpty(ratingStr)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        float rating;
        try {
            rating = Float.parseFloat(ratingStr);
            if (rating < 0 || rating > 10) {
                Toast.makeText(this, "Rating must be between 0 and 10", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid rating format", Toast.LENGTH_SHORT).show();
            return;
        }

        Movie movie = new Movie(title, studio, imageUrl, rating);
        if (isEdit) {
            db.collection("movies").document(movieId)
                    .set(movie)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Movie updated", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            db.collection("movies")
                    .add(movie)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(this, "Movie added", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Add failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}