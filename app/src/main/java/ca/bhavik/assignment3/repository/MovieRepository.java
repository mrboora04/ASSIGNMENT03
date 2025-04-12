package ca.bhavik.assignment3.repository;

import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import ca.bhavik.assignment3.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Movie>> movieListLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Movie>> getMovies() {
        db.collection("movies").limit(20)
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null || snapshot == null) {
                        movieListLiveData.postValue(new ArrayList<>());
                        return;
                    }
                    List<Movie> movies = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Movie m = doc.toObject(Movie.class);
                        m.setId(doc.getId());
                        movies.add(m);
                    }
                    movieListLiveData.postValue(movies);
                });
        return movieListLiveData;
    }

    public void addMovie(Movie movie) {
        db.collection("movies").add(movie);
    }

    public void updateMovie(String movieId, Movie movie) {
        db.collection("movies").document(movieId).set(movie);
    }

    public void deleteMovie(String movieId) {
        db.collection("movies").document(movieId).delete();
    }
}