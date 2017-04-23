package com.onionnews.demo.onionnews.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by asus on 2016/11/6.
 */

public class GuideViewPageAdapter extends PagerAdapter {
    ArrayList<View> list_view;
    public GuideViewPageAdapter(ArrayList<View> list_view){
        this.list_view = list_view;
    }
    @Override
    public int getCount() {
        return list_view.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //当前滑动的这一页需要添加显示的view

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //把viewgroup容器里面添加一页，内容为集合提供
        container.addView(list_view.get(position));
        //返回值为当前要显示的view
        return list_view.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //从Viewgroup容器里面移除
        container.removeView(list_view.get(position));
    }
}
