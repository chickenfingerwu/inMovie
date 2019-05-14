package com.inmovie.inmovie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inmovie.inmovie.Activities.TvActivities.TvDetails;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularShowAdapter extends RecyclerView.Adapter<PopularShowAdapter.showPosterViewHolder> {

    //data (which is a movies list)
    private List<TvShow> showList;
    private Context context;
    private LayoutInflater mInflater;


    //This class represents an item on the RecyclerView (e.g: in this case...
    //...a movie's poster make up an item)
    public static class showPosterViewHolder extends RecyclerView.ViewHolder {

        //Hold the poster image
        public ImageView imageView;

        //the item's data
        public TvShow tvShow;
        public showPosterViewHolder (View imgView){
            super(imgView);
            imageView = (ImageView) itemView.findViewById(R.id.test_poster);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setMovies(TvShow show){
            tvShow = show;
        }

        public TvShow getTvShow() {
            return tvShow;
        }

        //listen to user's activity, if user presses on a poster, it will launch the MovieDetails activity
        public void setListener(){

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TvDetails.class);
                    intent.putExtra("tvShow", tvShow);
                    context.startActivity(intent);
                }
            });
        }
    }

    public PopularShowAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.showList = new ArrayList<>();
    }

    //inflate the layout for the ViewHolder
    @Override
    public showPosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View img = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        showPosterViewHolder vh = new showPosterViewHolder(img);
        return vh;
    }

    /**Bind the data to a ViewHolder
     *
     * @param holder item on RecyclerView
     * @param position position of item
     */
    @Override
    public void onBindViewHolder(showPosterViewHolder holder, int position) {

        //get data from index 'position' from the movies list
        TvShow show = showList.get(position);

        //draw movies poster
        Picasso.with(context)
                .load(show.getPoster_url())
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);
        holder.setMovies(show);

        //set the listener to the item
        holder.setListener();
    }

    //return how many items there need to be
    @Override
    public int getItemCount(){
        return showList.size();
    }


    //use to add to the movies list, 'clear' is true if we want to delete the old list,
    // false if we want to keep the old list

    public void setShowList(List<TvShow> showList, boolean clear){
        if(clear){
            this.showList.clear();
        }
        this.showList.addAll(showList);
        notifyDataSetChanged();
    }
}
