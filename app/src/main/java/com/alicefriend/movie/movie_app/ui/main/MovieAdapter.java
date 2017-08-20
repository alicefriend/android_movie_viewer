package com.alicefriend.movie.movie_app.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.ui.detail.DetailActivity;
import com.alicefriend.movie.movie_app.util.Utils;

import java.util.List;

/**
 * Created by choi on 2017. 7. 31..
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> movies;

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }
        return movies.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_grid_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = itemView.findViewById(R.id.movie_thumnail);
        }

        public void bind(Movie movie){
            Utils.posterImageLoad(mImageView, movie);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("Movie", movies.get(getAdapterPosition()));
            context.startActivity(detailIntent);
        }
    }
}
