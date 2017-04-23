package com.onionnews.demo.onionnews.Utils;

import com.onionnews.demo.onionnews.DataBean.Url;

/**
 * Created by asus on 2016/11/1.
 */

public class GetUrl {



    /**
     * 获取 新闻头条 网址
     * @param index 需要加载的网页数
     * @return
     */
    public static String getNewsUrl(String classify,int index){

        String url_headline = new String();
        url_headline = Url.TopUrl+classify+"/"+index+ Url.endUrl;
        return url_headline;

    }

    /**
     * 广告
     * @param skipId 第一条新闻|广告 的url
     * @return
     */
    public static String getAdsUrl(String skipId){
        if (skipId.length()!=16)return "";
        return "http://c.3g.163.com/photo/api/set/"+skipId.substring(4,8)+"/"+skipId.substring(9)+".json ";
    }
}
