package com.onionnews.demo.onionnews.DataBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asus on 2016/10/27.
 */

public class TabDataBean {

    /**
     * template : normal1
     * topicid : 000181S1
     * hasCover : false
     * alias : The Truth
     * subnum : 超过1000万
     * recommendOrder : 0
     * isNew : 0
     * img : http://img2.cache.netease.com/m/newsapp/banner/zhenhua.png
     * isHot : 0
     * hasIcon : true
     * cid : C1348654575297
     * recommend : 0
     * headLine : false
     * color :
     * bannerOrder : 105
     * tname : 独家
     * ename : zhenhua
     * showType : comment
     * special : 0
     * tid : T1370583240249
     */

    private List<TListBean> tList;

    public List<TListBean> getTList() {
        return tList;
    }

    public void setTList(List<TListBean> tList) {
        this.tList = tList;
    }

    public static class TListBean implements Serializable{
        private String tname;
        private String tid;

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
