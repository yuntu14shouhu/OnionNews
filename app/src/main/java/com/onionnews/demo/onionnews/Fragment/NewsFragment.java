package com.onionnews.demo.onionnews.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.grumoon.pulllistview.PullListView;
import com.onionnews.demo.onionnews.Activity.NewsDetailsActivity;
import com.onionnews.demo.onionnews.Adapter.AdsViewPagerAdapter;
import com.onionnews.demo.onionnews.Adapter.NewsFragmentPullListAdapter;
import com.onionnews.demo.onionnews.ColumnView.AdsViewPager;
import com.onionnews.demo.onionnews.DataBean.NewsDataBean;
import com.onionnews.demo.onionnews.R;
import com.onionnews.demo.onionnews.Utils.GetUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 3016/10/18.
 */
public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private NewsFragmentPullListAdapter adapter_pl;
    private ArrayList<NewsDataBean.AdsBean> list_ads;
    private ArrayList<NewsDataBean> list_news;
    private PullListView plv_news;
    private AdsViewPager vp_ads_news;
    private AdsViewPagerAdapter adapter_ads;
    private int index;
    private boolean isFirst = true;
    private String classify;

    private GetUrl getUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        classify = args != null ?args.getString("classify"):"";
        getUrl = new GetUrl();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("list_news",list_news);
        outState.putSerializable("list_ads",list_ads);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            list_news = (ArrayList<NewsDataBean>) savedInstanceState.getSerializable("list_news");
            list_ads = (ArrayList<NewsDataBean.AdsBean>) savedInstanceState.getSerializable("list_ads");
        }else {
            list_news = new ArrayList<>();
            list_ads = new ArrayList<>();
        }
        View view = inflater.inflate(R.layout.news_viewpager_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        plv_news = (PullListView) view.findViewById(R.id.plv_news);
        View ads = getActivity().getLayoutInflater().inflate(R.layout.adsviewpager_pl_newsf,null);
        vp_ads_news = (AdsViewPager) ads.findViewById(R.id.ads_vp);

        plv_news.setBackgroundColor(0x353535);
        plv_news.setOnItemClickListener(this);
        plv_news.setOnRefreshListener(new PullListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index=0;
                getDatas(index,true);
            }
        });
        plv_news.setOnGetMoreListener(new PullListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                index+=10;
                getDatas(index,false);
            }
        });

        adapter_pl = new NewsFragmentPullListAdapter(getActivity(),list_news);
        adapter_ads = new AdsViewPagerAdapter(getActivity(),list_ads);
        vp_ads_news.setAdapter(adapter_ads);
        plv_news.addHeaderView(ads);
        plv_news.setAdapter(adapter_pl);

        plv_news.performRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int index = position-1;
        if ( index > -1 ){
            Intent intent = new Intent(getActivity(),NewsDetailsActivity.class);
            if ( list_news.get(index).getUrl()!=null ){
                intent.putExtra("url_item",list_news.get(index).getUrl());
            }else if (list_news.get(index).getSkipID()!=null){
                intent.putExtra("url_item",GetUrl.getAdsUrl(list_news.get(index).getSkipID()));
            }
            startActivity(intent);
        } else {
                Toast.makeText(getActivity(),"木有该链接!", Toast.LENGTH_SHORT).show();
        }
    }

    //联网获取数据
    public void getDatas(int index, final boolean isReflush){
        RequestQueue myQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest json_or = new JsonObjectRequest(
                getUrl.getNewsUrl(classify,index), null,
                        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                try {
                    if (isReflush && !isFirst){
                        list_news.clear();
                    }else {
                        isFirst = false;
                    }
                    JSONArray ja = jsonObject.getJSONArray(classify);
                    if (ja.length()!=0){
                        for (int i=0;i<ja.length();i++){
                            if (ja.get(i)!=null){
                                list_news.add(gson.fromJson(ja.getJSONObject(i).toString(),NewsDataBean.class));
                                if (i==0){
                                    if (list_news.get(0).getAds()!=null){
                                        list_ads = list_news.get(0).getAds();
                                    }
                                    list_ads.add(
                                            new NewsDataBean.AdsBean(
                                                    list_news.get(0).getTitle(),
                                                    list_news.get(0).getImgsrc(),
                                                    list_news.get(0).getSkipID()));
                                    Log.e("TAG", "onResponse: list_ads = "+list_ads);
                                }
                                Log.e("show", "onResponse: "+gson.fromJson(ja.getJSONObject(i).toString(),NewsDataBean.class));
                            }else {
                                continue;
                            }
                        }
                    }
                    adapter_ads.setList_ads(list_ads);
                    if(list_news.size()>0){
                        adapter_pl.setList_news(new ArrayList<>(list_news.subList(1,list_news.size())));
                    }
                    if (isReflush){
                        plv_news.refreshComplete();
                    }else {
                        plv_news.getMoreComplete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity()!=null){
                    Toast.makeText(getActivity(),"资源获取失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        myQueue.add(json_or);
    }

}
