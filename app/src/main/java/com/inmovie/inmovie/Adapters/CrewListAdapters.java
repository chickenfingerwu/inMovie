package com.inmovie.inmovie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.Actor;
import com.inmovie.inmovie.Crew;
import com.inmovie.inmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CrewListAdapters extends RecyclerView.Adapter<CrewListAdapters.crewViewHolder> {
    private List<Crew> crews;
    private Context c;
    private LayoutInflater layoutInflater;

    public CrewListAdapters(Context context) {
        this.c = context;
        this.crews = new ArrayList<>();
    }

    public class crewViewHolder extends RecyclerView.ViewHolder{
        CircleImageView crewProfile;
        TextView crewName;
        TextView crewRole;

        public crewViewHolder(View actorView){
            super(actorView);

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


    @Override
    public CrewListAdapters.crewViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View list = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_role_field, parent, false);
        CrewListAdapters.crewViewHolder vh = new CrewListAdapters.crewViewHolder(list);
        return vh;
    }

    @Override
    public void onBindViewHolder(CrewListAdapters.crewViewHolder vh, int position){
        Crew crew1 = crews.get(position);

        if(crew1.getProfilePic() != null) {
            Picasso.with(c)
                    .load(crew1.getProfilePic())
                    .noFade()
                    .placeholder(R.color.grey)
                    .into(vh.crewProfile);
        }

        vh.crewName.setText(crew1.getCrewName());
        vh.crewRole.setText(crew1.getRole());

    }

    @Override
    public int getItemCount(){
        return crews.size();
    }

    public void setCrew(List<Crew> crew, boolean clear) {
        if(clear){
            this.crews.clear();
        }
        this.crews.addAll(crew);
        notifyDataSetChanged();
    }

}
