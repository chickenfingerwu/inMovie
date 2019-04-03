package com.inmovie.inmovie;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.model.api.TMDb.Movies.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetDetails;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {

    Movies movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get Intent that started this activity and extract string
        Intent intent = getIntent();
        movie = (Movies) intent.getSerializableExtra("serialize_data");

        TextView overview = findViewById(R.id.movieDescription);

        ImageView banner = findViewById(R.id.poster_banner);

        TextView contentName = findViewById(R.id.content_name);

        TextView numberRating = findViewById(R.id.numberRating);

        RatingBar ratingBar = findViewById(R.id.ratingBar3);
        ratingBar.setNumStars(10);

        TextView additionalInfo = (TextView) findViewById(R.id.additionalInfo);
        TextView genres_runtime = (TextView) findViewById(R.id.genres_runtime);

        TextView releseDate = (TextView) findViewById(R.id.release_date);

        ArrayList<View> views = new ArrayList<>();
        views.add(contentName);
        views.add(overview);
        views.add(banner);
        views.add(numberRating);
        views.add(ratingBar);
        views.add(additionalInfo);
        views.add(genres_runtime);
        views.add(releseDate);

        contentName.setTextColor(Color.WHITE);

        new GetDetails(views).execute(movie.getId());


        LinearLayout castList = (LinearLayout) findViewById(R.id.list);
        JSONArray castJSON = null;
        try{
            JSONObject jsonObject = new GetCredits().execute(movie.getId()).get();
            castJSON = jsonObject.getJSONArray("cast");
            System.out.println(castJSON.length());
            String name = "";
            String role = "";
            for(int i = 0; i < castJSON.length(); i++){
                JSONObject actor = castJSON.getJSONObject(i);
                name = actor.getString("name");
                role = actor.getString("character");

                Typeface font = ResourcesCompat.getFont(this, R.font.oswald_light);

                ConstraintLayout.LayoutParams paramsName = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                TextView nameView = new TextView(this);
                nameView.setText(name);
                nameView.setId(i);
                nameView.setTextSize(13);
                nameView.setPadding(16,0,0,0);
                nameView.setTextColor(Color.WHITE);
                nameView.setTypeface(font);
                nameView.setLayoutParams(paramsName);

                ConstraintLayout.LayoutParams paramsRole = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                TextView roleView = new TextView(this);
                roleView.setText(role);
                roleView.setId(i + 1);
                roleView.setTextSize(13);
                roleView.setPadding(0, 0, 32, 0);
                roleView.setTextColor(Color.WHITE);
                roleView.setTypeface(font);
                roleView.setLayoutParams(paramsRole);


                ConstraintLayout constraintLayout = new ConstraintLayout(this);
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                constraintLayout.setLayoutParams(layoutParams);
                constraintLayout.addView(nameView, 0);
                constraintLayout.addView(roleView, 1);
                ConstraintSet set = new ConstraintSet();
                set.clone(constraintLayout);
                set.connect(nameView.getId(), ConstraintSet.LEFT,ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32);
                set.connect(roleView.getId(), ConstraintSet.RIGHT,ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 16);
                set.applyTo(constraintLayout);
                castList.addView(constraintLayout);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //float numRating = Float.parseFloat(numberRating.getText().toString());
        //ratingBar.setRating(numRating);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
