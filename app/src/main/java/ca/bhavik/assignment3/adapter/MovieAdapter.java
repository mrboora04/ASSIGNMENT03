package ca.bhavik.assignment3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ca.bhavik.assignment3.databinding.ItemMovieBinding;
import ca.bhavik.assignment3.model.Movie;
import com.bumptech.glide.Glide;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList, Context context, OnMovieClickListener listener) {
        this.movieList = movieList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.binding.movieTitle.setText(movie.getTitle() != null ? movie.getTitle() : "N/A");
        holder.binding.movieYear.setText(movie.getYear() != null ? movie.getYear() : "N/A");
        holder.binding.movieRating.setText("IMDb: " + (movie.getImdbRating() != null ? movie.getImdbRating() : "N/A"));

        if (movie.getPoster() != null && !movie.getPoster().equals("N/A")) {
            Glide.with(context)
                    .load(movie.getPoster())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(holder.binding.moviePoster);
        } else {
            holder.binding.moviePoster.setImageResource(R.drawable.placeholder_image);
        }

        holder.binding.getRoot().setOnClickListener(v -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        public MovieViewHolder(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}