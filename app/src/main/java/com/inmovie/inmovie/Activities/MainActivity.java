package com.inmovie.inmovie.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.inmovie.inmovie.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setSubmitButtonEnabled(true);
        searchBar.setFocusable(false);
        searchBar.setIconifiedByDefault(false);
        searchBar.clearFocus();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, MoviesSearchResult.class);
                intent.putExtra("movie_searched", query);
                MainActivity.this.startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        TextView checkPopular = (TextView) findViewById(R.id.checkPopular);
        checkPopular.setPaintFlags(checkPopular.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    public void startPopularView(View view){
        Intent intent = new Intent(this, PopularMovies.class);
        startActivity(intent);
    }
}
