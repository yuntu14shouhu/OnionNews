package com.onionnews.demo.onionnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.onionnews.demo.onionnews.Activity.NewsDetailsActivity;
import com.onionnews.demo.onionnews.DataBean.NewsDataBean;
import com.onionnews.demo.onionnews.R;
import com.onionnews.demo.onionnews.Utils.AnimateFirstDisplayListener;
import com.onionnews.demo.onionnews.Utils.GetUrl;

import java.util.List;

/**
 * Created by asus on 2016/11/1.
 */

public class AdsViewPagerAdapter extends PagerAdapter{
    private List<NewsDataBean.AdsBean> list_ads;
    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public void setList_ads(List<NewsDataBean.AdsBean> list_ads) {
        this.list_ads = list_ads;
        notifyDataSetChanged();
    }

    public AdsViewPagerAdapter(Context context, List<NewsDataBean.AdsBean> list_ads) {
        this.list_ads = list_ads;
        this.context = context;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_default)//设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.defaul_fail)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.defaul_fail)//设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//是否緩存都內存中
                .cacheOnDisc(true)//是否緩存到sd卡上
                .displayer(new RoundedBitmapDisplayer(3))
                .build();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ads_viewpager,null);

        RelativeLayout rl_click = (RelativeLayout) view.findViewById(R.id.ads_click);
        rl_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewsDetailsActivity.class);
                if (list_ads.get(position).getUrl()!=null){
                    String url_item = GetUrl.getAdsUrl(list_ads.get(position).getUrl());
                    intent.putExtra("url_item",url_item);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context,"木有该链接!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView img = (ImageView) view.findViewById(R.id.ads_iv);
        TextView tv = (TextView) view.findViewById(R.id.ads_tv);
        Log.e("adsdata", "instantiateItem: "+list_ads);
        tv.setText(list_ads.get(position).getTitle());
//        img.setImageBitmap(null);
        Log.e("==url==", "instantiateItem: "+list_ads.get(position).getImgsrc());
        imageLoader.displayImage(list_ads.get(position).getImgsrc(), img, options, animateFirstListener);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list_ads.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

}
