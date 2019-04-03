package com.inmovie.inmovie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.posterViewHolder> {

    private List<Movies> moviesList;
    private Context context;
    private LayoutInflater mInflater;

    public static class posterViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public Movies movies;
        public posterViewHolder (View imgView){
            super(imgView);
            imageView = (ImageView) itemView.findViewById(R.id.test_poster);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setMovies(Movies m){
            movies = m;
        }

        public Movies getMovies() {
            return movies;
        }

        public void setListener(){

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("serialize_data", movies);
                    context.startActivity(intent);
                }
            });
        }
    }

    public ImageAdapter(Context context) {
        this.context = context;
        //this.bitmapList = bitmapList;
        this.mInflater = LayoutInflater.from(context);
        this.moviesList = new ArrayList<>();
    }

    @Override
    public posterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View img = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        posterViewHolder vh = new posterViewHolder(img);
        return vh;
    }

    @Override
    public void onBindViewHolder(posterViewHolder holder, int position) {
        //holder.imageView.setImageResource(R.drawable.annihilation_poster);
        Movies movie = moviesList.get(position);
        Picasso.with(context)
                .load(movie.getPoster_url())
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);
        holder.setMovies(movie);
        holder.setListener();
    }

    @Override
    public int getItemCount(){
        return moviesList.size();
    }

    public void setMoviesList(List<Movies> movieList, boolean clear)
    {
        if(clear) {
            this.moviesList.clear();
        }
        this.moviesList.addAll(movieList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    public void getMoreMovies(){
        for(int i = 0; i < 25; i++) {
            this.moviesList.add(new Movies());
        }
        notifyDataSetChanged();
    }


}
