package com.onionnews.demo.onionnews.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置RecycleView边距 空边距
 * Created by asus on 2016/10/27.
 */

public class DividerRecyclerViewItem extends RecyclerView.ItemDecoration{
    private int pixels;

    public DividerRecyclerViewItem(Context context,int dp) {
        this.pixels = DensityUtil.dipToPx(context,dp);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = pixels;
        outRect.right = pixels;
        outRect.top = pixels;
    }
}
