package com.inmovie.inmovie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.Actor;
import com.inmovie.inmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastListAdapters extends RecyclerView.Adapter<CastListAdapters.actorViewHolder> {
    private List<Actor> actors;
    private Context c;
    private LayoutInflater layoutInflater;

    public CastListAdapters(Context context) {
        this.c = context;
        this.actors = new ArrayList<>();
    }

    public class actorViewHolder extends RecyclerView.ViewHolder{
        CircleImageView actorProfile;
        TextView actorName;
        TextView actorRole;

        public actorViewHolder(View actorView){
            super(actorView);

            actorProfile = (CircleImageView) itemView.findViewById(R.id.actor_profile);
            actorName = (TextView) itemView.findViewById(R.id.actor_name);
            actorRole = (TextView) itemView.findViewById(R.id.actor_character);
        }

        public ImageView getActorProfile() {
            return actorProfile;
        }

        public TextView getActorName() {
            return actorName;
        }

        public TextView getActorRole() {
            return actorRole;
        }

        public void setActorName(TextView actorName) {
            this.actorName = actorName;
        }

        public void setActorProfile(CircleImageView actorProfile) {
            this.actorProfile = actorProfile;
        }

        public void setActorRole(TextView actorRole) {
            this.actorRole = actorRole;
        }
    }


    @Override
    public CastListAdapters.actorViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View list = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.name_character_field, parent, false);
        CastListAdapters.actorViewHolder vh = new actorViewHolder(list);
        return vh;
    }

    @Override
    public void onBindViewHolder(CastListAdapters.actorViewHolder vh, int position){
        Actor actor1 = actors.get(position);

        if(actor1.getProfilePic() != null) {
            Picasso.with(c)
                    .load(actor1.getProfilePic())
                    .noFade()
                    .placeholder(R.color.grey)
                    .into(vh.actorProfile);
        }

        vh.actorName.setText(actor1.getActorName());
        vh.actorRole.setText(actor1.getRole());

    }

    @Override
    public int getItemCount(){
        return actors.size();
    }

    public void setActor(List<Actor> actor, boolean clear) {
        if(clear){
            this.actors.clear();
        }
        this.actors.addAll(actor);
        notifyDataSetChanged();
    }


}
