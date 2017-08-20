package com.alicefriend.movie.movie_app.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.domain.Trailer;

import java.util.List;

/**
 * Created by choi on 2017. 8. 1..
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String TAG = TrailerAdapter.class.getSimpleName();

    private List<Trailer> trailers;

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        Log.d(TAG, "setTrailers: " + trailers.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(trailers == null) {
            return 0;
        }
        return trailers.size();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_list_item, parent, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder viewHolder, int position) {
        viewHolder.trailerName.setText(trailers.get(position).getName());
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView trailerName;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Trailer trailer = trailers.get(getAdapterPosition());
            final String baseUrl = "https://www.youtube.com/watch";

            Uri youtubeUri = Uri.parse(baseUrl).buildUpon().appendQueryParameter("v", trailer.getKey()).build();
            v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, youtubeUri));
        }
    }
}
