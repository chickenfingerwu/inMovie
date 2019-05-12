package com.inmovie.inmovie;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SideSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SideSpaceItemDecoration(int s){
        space = s;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space/2;
        outRect.right = space/2;
        if(parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 0;
        }
        else {
            outRect.left = space/2;
        }
    }
}
