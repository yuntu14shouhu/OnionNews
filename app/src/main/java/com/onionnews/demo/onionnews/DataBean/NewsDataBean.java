package com.onionnews.demo.onionnews.DataBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/10/13.
 */
public class NewsDataBean implements Serializable{

    /**
     * votecount : 4382
     * replyCount : 4631
     * title : 台风"莎莉嘉"逼近海南 生活物资被抢空
     * source : 网易原创
     * imgsrc : http://cms-bucket.nosdn.127.net/e7e4ab51d4a246d7acfe19c99a3ef08e20161017174132.jpeg
     * tname : 头条
     * ptime : 2016-10-17 17:41:44
     */
    private String title;
    private int replyCount;
    private String url;
    private int votecount;
    private String source;
    private String imgsrc;
    private String skipID;
    private ArrayList<AdsBean> ads;

    public NewsDataBean(String title, int replyCount, String url, int votecount, String source, String imgsrc, String skipID, ArrayList<AdsBean> ads) {
        this.title = title;
        this.replyCount = replyCount;
        this.url = url;
        this.votecount = votecount;
        this.source = source;
        this.imgsrc = imgsrc;
        this.skipID = skipID;
        this.ads = ads;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public ArrayList<AdsBean> getAds() {
        return ads;
    }

    public void setAds(ArrayList<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean implements Serializable{
        private String title;
        private String imgsrc;
        private String url;

        public AdsBean(String title, String imgsrc, String url) {
            this.title = title;
            this.imgsrc = imgsrc;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
