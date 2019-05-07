package com.inmovie.inmovie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inmovie.inmovie.Activities.MoviesActivities.MovieDetails;
import com.inmovie.inmovie.Activities.TvDetails;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.TVclasses.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrendingsAdapter extends RecyclerView.Adapter<TrendingsAdapter.trendingViewHolder> {
    //data (which is a movies list)
    private List<Movies> moviesList;
    private Context context;

    //This class represents an item on the RecyclerView (e.g: in this case...
    //...a movie's poster make up an item)
    public static class trendingViewHolder extends RecyclerView.ViewHolder {

        //Hold the poster image
        public ImageView imageView;

        //the item's data
        public Movies movies;
        public trendingViewHolder (View imgView){
            super(imgView);
            imageView = (ImageView) itemView.findViewById(R.id.trending_poster);
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

        //listen to user's activity, if user presses on a poster, it will launch the MovieDetails activity
        public void setListener(){

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = null;
                    if(movies instanceof TvShow){
                        intent = new Intent(context, TvDetails.class);
                        intent.putExtra("tvShow", movies);
                    }
                    else if(movies instanceof Movies) {
                        intent = new Intent(context, MovieDetails.class);
                        intent.putExtra("serialize_data", movies);
                    }
                    context.startActivity(intent);
                }
            });
        }
    }

    public TrendingsAdapter(Context context) {
        this.context = context;
        this.moviesList = new ArrayList<>();
        for(int i = 0; i < 25; i++){
            moviesList.add(new Movies());
        }
    }

    //inflate the layout for the ViewHolder
    @Override
    public trendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View img = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_item, parent, false);
        trendingViewHolder vh = new trendingViewHolder(img);
        return vh;
    }

    /**Bind the data to a ViewHolder
     *
     * @param holder item on RecyclerView
     * @param position position of item
     */
    @Override
    public void onBindViewHolder(trendingViewHolder holder, int position) {

        //get data from index 'position' from the movies list
        Movies movie = moviesList.get(position);

        //draw movies poster
        Picasso.with(context)
                .load(movie.getPoster_url())
                .placeholder(R.color.grey)
                .into(holder.imageView);
        holder.setMovies(movie);

        //set the listener to the item
        holder.setListener();
    }

    //return how many items there need to be
    @Override
    public int getItemCount(){
        return moviesList.size();
    }


    //use to add to the movies list, 'clear' is true if we want to delete the old list,
    // false if we want to keep the old list
    public void setMoviesList(List<Movies> movieList, boolean clear)
    {
        if(clear) {
            this.moviesList.clear();
        }
        this.moviesList.addAll(movieList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    //ignore this function
    public void getMoreMovies(){
        for(int i = 0; i < 25; i++) {
            this.moviesList.add(new Movies());
        }
        notifyDataSetChanged();
    }

}
