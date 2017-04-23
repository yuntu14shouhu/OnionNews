package com.onionnews.demo.onionnews.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.onionnews.demo.onionnews.ColumnView.CircleImageView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by asus on 2016/11/3.
 */

public class QQlogin {
    private static QQlogin qqLogin ;
    private Context context;
    private CircleImageView civ_user_icon;
    private TextView tv_user_name;
    private Button btn_login;
    //授权qq
    private Platform qq;

    public Platform getQq() {
        return qq;
    }

    public static QQlogin getQQLogin(Context context, CircleImageView civ_user_icon, TextView tv_user_name, Button btn_login) {
        if (qqLogin == null){
            qqLogin = new QQlogin(context, civ_user_icon, tv_user_name, btn_login);
        }
        return qqLogin;
    }

    private QQlogin(Context context, CircleImageView civ_user_icon, TextView tv_user_name, Button btn_login) {
        this.context = context;
        this.civ_user_icon = civ_user_icon;
        this.tv_user_name = tv_user_name;
        this.btn_login = btn_login;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                tv_user_name.setText(((Platform) msg.obj).getDb().getUserName());
                btn_login.setText("注销");
                getIcon(((Platform) msg.obj).getDb().getUserIcon());
            }
        }
    };
    /**
     * 通过volley获取图片
     * @param url
     */
    public void getIcon(String url) {
        RequestQueue queue = Volley.newRequestQueue(context);

        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                civ_user_icon.setImageBitmap(bitmap);
            }
        },
                DensityUtil.dipToPx(context,125),
                DensityUtil.dipToPx(context,125),
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "图标消失在二次元了..", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }

    /**
     * 授权操作
     */
    public void getPlatform() {
        qq = ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                arg2.printStackTrace();
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                Message msg = handler.obtainMessage();
                Log.e("hashmap", "onComplete: "+arg2 );
                msg.what = 0;
                msg.obj = arg0;
                handler.sendMessage(msg);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                handler.sendEmptyMessage(1);
            }
        });

        //authorize与showUser单独调用一个即可
        qq.authorize();//单独授权,OnComplete返回的hashmap是空的

        //qq.showUser(null);//授权并获取用户信息
    }
}
