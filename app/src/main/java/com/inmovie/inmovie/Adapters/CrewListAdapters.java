package com.inmovie.inmovie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.inmovie.inmovie.MovieTvClasses.Crew;
import com.inmovie.inmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Used when inside a MovieDetails or TvDetails activity, use to
 * manages the data to be display of the item in the crew list's RecyclerView
 * to the user
 */

public class CrewListAdapters extends RecyclerView.Adapter<CrewListAdapters.crewViewHolder> {

    //data (crews)
    private List<Crew> crews;
    private Context c;
    private LayoutInflater layoutInflater;

    public CrewListAdapters(Context context) {
        this.c = context;

        //instantiate data
        this.crews = new ArrayList<>();
    }


    //This class represents an item on the RecyclerView (e.g: in this case...
    //...a crew member's profile pic and name and role make up an item)
    public class crewViewHolder extends RecyclerView.ViewHolder{

        //Hold the crew member's profile picture
        CircleImageView crewProfile;

        //Hold the crew member's name
        TextView crewName;

        //Hold the actor's role
        TextView crewRole;

        public crewViewHolder(View actorView){
            super(actorView);

            //find the views with their IDs inside the ViewHolder's layout
            crewProfile = (CircleImageView) itemView.findViewById(R.id.crew_profile);
            crewName = (TextView) itemView.findViewById(R.id.crew_name);
            crewRole = (TextView) itemView.findViewById(R.id.crew_role);
        }

        public ImageView getCrewProfile() {
            return crewProfile;
        }

        public TextView getCrewName() {
            return crewName;
        }

        public TextView getCrewRole() {
            return crewRole;
        }

        public void setCrewName(TextView crewName) {
            this.crewName = crewName;
        }

        public void setCrewProfile(CircleImageView crewProfile) {
            this.crewProfile = crewProfile;
        }

        public void setCrewRole(TextView crewRole) {
            this.crewRole = crewRole;
        }
    }

    //inflate the layout for the ViewHolder
    @Override
    public CrewListAdapters.crewViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View list = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_role_field, parent, false);
        CrewListAdapters.crewViewHolder vh = new CrewListAdapters.crewViewHolder(list);
        return vh;
    }

    //Bind the data to a ViewHolder
    @Override
    public void onBindViewHolder(CrewListAdapters.crewViewHolder vh, int position){
        //Get data at index 'position' from the crews list
        Crew crew1 = crews.get(position);

        //Draw crew member's profile pic if it's not null
        if(!crew1.getProfilePic().equals("")) {
            Picasso.with(c)
                    .load(crew1.getProfilePic())
                    .noFade()
                    .placeholder(R.color.grey)
                    .into(vh.crewProfile);
        }
        else {
            TextDrawable placeholder = TextDrawable.builder().buildRound(crew1.getCrewName(), R.color.grey);
            vh.crewProfile.setImageDrawable(placeholder);
        }

        //set crew member's display name and role
        vh.crewName.setText(crew1.getCrewName());
        vh.crewRole.setText(crew1.getRole());

    }

    //return how many items there need to be
    @Override
    public int getItemCount(){
        return crews.size();
    }

    //use to add to the crews list, 'clear' is true if we want to delete the old list,
    // false if we want to keep the old list
    public void setCrew(List<Crew> crew, boolean clear) {
        if(clear){
            this.crews.clear();
        }
        this.crews.addAll(crew);
        notifyDataSetChanged();
    }

}
