package com.inmovie.inmovie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.Activities.TvActivities.EpisodeDetails;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.Episode;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TvEpisodeResultAdapter extends RecyclerView.Adapter<TvEpisodeResultAdapter.tvEpisodeHolder> {
    private List<Episode> episodes;
    protected Context context;
    protected LayoutInflater layoutInflater;

    public TvEpisodeResultAdapter(Context c){
        context = c;
        episodes = new ArrayList<>();
    }

    public class tvEpisodeHolder extends RecyclerView.ViewHolder{
        private ImageView thumbnail;
        private TextView episodeName;
        private TextView episodeOverview;
        private Episode episode;

        private tvEpisodeHolder (View epView){
            super(epView);
            thumbnail = (ImageView) itemView.findViewById(R.id.tv_episode_thumbnail);
            episodeName = (TextView) itemView.findViewById(R.id.tv_episode_name);
            episodeOverview = (TextView) itemView.findViewById(R.id.tv_episode_overview);
            epView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EpisodeDetails.class);
                    intent.putExtra("serialize_data", episode);
                    context.startActivity(intent);
                }
            });
        }

        public void setTvEp(Episode e){
            episode = e;
        }

        public Episode getEpisode() {
            return episode;
        }

    }

    @Override
    public TvEpisodeResultAdapter.tvEpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View epView = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_item, parent, false);
        TvEpisodeResultAdapter.tvEpisodeHolder vh = new TvEpisodeResultAdapter.tvEpisodeHolder(epView);
        return vh;
    }

    @Override
    public void onBindViewHolder(TvEpisodeResultAdapter.tvEpisodeHolder holder, int position){
        Episode ep = episodes.get(position);

        if(ep.getBackdrop() != null) {
            Picasso.with(context)
                    .load(ep.getPoster_url())
                    .placeholder(R.color.grey)
                    .into(holder.thumbnail);
        }

        holder.setTvEp(ep);
        TextView name = holder.episodeName;
        TextView overView = holder.episodeOverview;
        String source = "";
        source = ep.getTitle();
        overView.setText(ep.getDescription());
        name.setText(source);
    }

    @Override
    public int getItemCount(){
        return episodes.size();
    }

    public void setEpisodesList(List<Episode> eList, boolean clear)
    {
        if(clear) {
            this.episodes.clear();
        }
        this.episodes.addAll(eList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }
}
