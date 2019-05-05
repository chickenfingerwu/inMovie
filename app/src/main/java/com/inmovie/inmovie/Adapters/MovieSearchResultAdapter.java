package com.inmovie.inmovie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.Activities.MoviesActivities.MovieDetails;
import com.inmovie.inmovie.Activities.TvDetails;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.TVclasses.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchResultAdapter extends RecyclerView.Adapter<MovieSearchResultAdapter.searchResultHolder> {
    private List<Movies> resultList;
    protected Context context;
    protected LayoutInflater layoutInflater;

    public static class searchResultHolder extends RecyclerView.ViewHolder{
        private ImageView poster;
        private TextView title_year;
        private Movies movieTv;

        public searchResultHolder (View searchView){
            super(searchView);
            poster = (ImageView) itemView.findViewById(R.id.search_result_poster);
            title_year = (TextView) itemView.findViewById(R.id.title_year);

            searchView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = null;
                    if(movieTv instanceof TvShow){
                        intent = new Intent(context, TvDetails.class);
                        intent.putExtra("tvShow", movieTv);
                    }
                    else if(movieTv instanceof Movies) {
                        intent = new Intent(context, MovieDetails.class);
                        intent.putExtra("movie", movieTv);
                    }
                    intent.putExtra("serialize_data", movieTv);
                    context.startActivity(intent);
                }
            });
        }

        public ImageView getPoster() {
            return poster;
        }

        public void setMovieTv(Movies m){
            movieTv = m;
        }

        public Movies getMovieTv() {
            return movieTv;
        }

        public TextView getTitle_year() {
            return title_year;
        }
    }

    public MovieSearchResultAdapter(Context context){
        this.context = context;
        this.resultList = new ArrayList<>();
    }

    @Override
    public MovieSearchResultAdapter.searchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View search = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        MovieSearchResultAdapter.searchResultHolder vh = new MovieSearchResultAdapter.searchResultHolder(search);
        return vh;
    }

    @Override
    public void onBindViewHolder(MovieSearchResultAdapter.searchResultHolder holder, int position) {
        //holder.imageView.setImageResource(R.drawable.annihilation_poster);
        Movies mTV = resultList.get(position);
        if(mTV.getBackdrop() != null) {
            Picasso.with(context)
                    .load(mTV.getPoster_url())
                    .resize(240, 360)
                    .placeholder(R.color.grey)
                    .into(holder.poster);
        }
        else {

        }
        holder.setMovieTv(mTV);
        TextView titleY = holder.title_year;
        titleY.setTextColor(Color.WHITE);
        String source = "";
        source += mTV.getTitle();
        if(!mTV.getReleaseDate().equals("")) {
            String[] releaseDate = mTV.getReleaseDate().split("-");
            source += " (" + releaseDate[0] + ")";
        }
        else {
            source += " (Unknown release date)";
        }
        titleY.setText(source);
    }

    @Override
    public int getItemCount(){
        return resultList.size();
    }

    public void setMoviesList(List<Movies> movieList, boolean clear)
    {
        if(clear) {
            this.resultList.clear();
        }
        this.resultList.addAll(movieList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    public void clearMoviesList(){
        this.resultList.clear();
        notifyDataSetChanged();
    }

}
