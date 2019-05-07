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

/**
 * Used when inside a MovieDetails or TvDetails activity, use to
 * manages the data to be display of the item in the cast list's RecyclerView
 * to the user
 */


public class CastListAdapters extends RecyclerView.Adapter<CastListAdapters.actorViewHolder> {

    //data (actors)
    private List<Actor> actors;

    private Context c;
    private LayoutInflater layoutInflater;

    public CastListAdapters(Context context) {
        this.c = context;

        //instantiate data
        this.actors = new ArrayList<>();
    }

    //This class represents an item on the RecyclerView (e.g: in this case...
    //...an actor's profile pic and name and role make up an item)
    public class actorViewHolder extends RecyclerView.ViewHolder{

        //Hold the actor's profile picture
        CircleImageView actorProfile;

        //Hold the actor's name
        TextView actorName;

        //Hold the actor's role
        TextView actorRole;

        public actorViewHolder(View actorView){
            super(actorView);

            //find the views with their IDs inside the ViewHolder's layout
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

        //Inflate the layout for the item (which is a ViewHolder)
        View list = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.name_character_field, parent, false);
        CastListAdapters.actorViewHolder vh = new actorViewHolder(list);
        return vh;
    }

    //Binding the data to a ViewHolder
    @Override
    public void onBindViewHolder(CastListAdapters.actorViewHolder vh, int position){

        //Get data at index 'position' from the actors list
        Actor actor1 = actors.get(position);

        //draw the profile pic for the actor if actor's profile pic is not null
        if(actor1.getProfilePic() != null) {
            Picasso.with(c)
                    .load(actor1.getProfilePic())
                    .noFade()
                    .placeholder(R.color.grey)
                    .into(vh.actorProfile);
        }

        //set the display name and role for the actor
        vh.actorName.setText(actor1.getActorName());
        vh.actorRole.setText(actor1.getRole());

    }


    //return how many items there need to be
    @Override
    public int getItemCount(){
        return actors.size();
    }


    //use to add to the actors list, 'clear' is true if we want to delete the old list,
    // false if we want to keep the old list
    public void setActor(List<Actor> actor, boolean clear) {
        if(clear){
            this.actors.clear();
        }
        this.actors.addAll(actor);
        notifyDataSetChanged();
    }


}
