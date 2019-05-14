package com.inmovie.inmovie.Decorations;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public DividerItemDecoration(Drawable divider) {
        mDivider = divider;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state){
        super.onDraw(c, parent, state);
        int startingPoint = 46;
        int endingPoint = parent.getWidth();

        for(int i = 0; i < parent.getChildCount(); i++){
            if(i != parent.getChildCount() - 1){
                View child  = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int dividerTop = child.getBottom() + params.bottomMargin;
                int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();

                mDivider.setBounds(startingPoint, dividerTop, endingPoint,dividerBottom);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //We do not want to add any padding for the first child
        //Because we do not want to have any unwanted space above the
        //Recycler view
        if(parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        //For every child, add some padding on the top
        //Intrinsic height method returns the actual size of the image
        //If the image size is 500*345 and you set in the XML
        //That the image view height and width is 200*200.
        //The when you call getIntrinsicHeight you get 500 and not 200
        //But when you call getHeight, you'll get 200.
        outRect.top = mDivider.getIntrinsicHeight();
    }

}
