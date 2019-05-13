package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.model.api.Network;
import com.squareup.picasso.Picasso;
import com.victor.loading.newton.NewtonCradleLoading;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONObject;

import at.blogc.android.views.ExpandableTextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class GetReviews extends AsyncTask<Integer, Void, JSONObject> {

    private ProgressBar loadingAnimation;
    private int page;
    private LinearLayout reviews;
    private Context context;
    private TextView seeMore;

    public GetReviews(){}


    public GetReviews(LinearLayout reviews, int page) {
        this.reviews = reviews;
        this.page = page;
        context = reviews.getContext();
    }

    public GetReviews(LinearLayout reviews, int page, ProgressBar loadingAnimation) {
        this.reviews = reviews;
        this.page = page;
        this.loadingAnimation = loadingAnimation;
        context = reviews.getContext();
    }

    // TODO: Create another constructor with Views

    /**
     * Get reviews
     * @param integers Movie's ID and page
     * @return JSONObject
     */
    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/movie/" + integers[0] + "/reviews?api_key=" + BuildConfig.TMDb_API_key  + "&page=" + page);

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        // TODO: Set something here
        try {
            int total_page = jsonObject.getInt("total_pages");
            if(page <= total_page){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                JSONArray pageReviews = jsonObject.getJSONArray("results");
                JSONObject reviewer = null;
                for(int i = 0; i < pageReviews.length(); i++){
                    LinearLayout item =  (LinearLayout) inflater.inflate(R.layout.review_item, null);
                    reviewer = pageReviews.getJSONObject(i);

                    String author = reviewer.getString("author");
                    String content = reviewer.getString("content");

                    ImageView profileImage = item.findViewById(R.id.reviewer_profile);
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = generator.getRandomColor();
                    TextDrawable placeHolder = TextDrawable.builder().buildRound(Character.toString(author.charAt(0)), color1);
                    profileImage.setImageDrawable(placeHolder);

                    TextView authorName = item.findViewById(R.id.reviewer_name);
                    authorName.setText(author);
                    ExpandableTextView reviewContent = item.findViewById(R.id.review_content);
                    reviewContent.setText(content);

                    reviewContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reviewContent.toggle();
                        }
                    });

                    reviews.addView(item, reviews.getChildCount());
                }
            }
            loadingAnimation.setVisibility(View.GONE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
