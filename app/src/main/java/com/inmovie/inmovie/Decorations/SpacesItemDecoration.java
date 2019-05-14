package com.inmovie.inmovie.Decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int s){
        space = s;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space/2;
        outRect.right = space/2;
        outRect.bottom = space/2;
        if(parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = space;
        }
        else {
            outRect.top = space/2;
        }
    }
}
