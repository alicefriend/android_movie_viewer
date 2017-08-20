package com.alicefriend.movie.movie_app.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.domain.Review;

import java.util.List;

/**
 * Created by choi on 2017. 8. 1..
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();

    private List<Review> reviews;

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        Log.d(TAG, "setReviews: " +reviews.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        ReviewHolder viewHolder = new ReviewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.author.setText(reviews.get(position).getAuthor());
        holder.content.setText(reviews.get(position).getContent());
    }

    class ReviewHolder extends RecyclerView.ViewHolder {

        TextView content;
        TextView author;

        public ReviewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.review);
            author = itemView.findViewById(R.id.author);
        }
    }
}
