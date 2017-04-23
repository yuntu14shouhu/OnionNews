package com.onionnews.demo.onionnews.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.onionnews.demo.onionnews.ColumnView.CircleImageView;
import com.onionnews.demo.onionnews.R;
import com.onionnews.demo.onionnews.Utils.QQlogin;
import com.onionnews.demo.onionnews.Utils.ShareSdkTools;

/**
 * Created by asus on 2016/11/3.
 */

public class DrawerLayoutFragment extends Fragment{
    private Button btn_share;
    private CircleImageView civ_user_icon;
    private TextView tv_user_name;
    private Button btn_login;
    private QQlogin qqlogin;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_fragment_drawerlayout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_share = (Button) view.findViewById(R.id.share_btn);
        //设置监听--分享按钮
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSdkTools.showShare(getActivity());
            }
        });
        civ_user_icon = (CircleImageView) view.findViewById(R.id.icon_user_civ);
        tv_user_name = (TextView) view.findViewById(R.id.name_user_tv);
        btn_login = (Button) view.findViewById(R.id.login_fast_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("frag", "onClick: ");
                qqlogin = QQlogin.getQQLogin(getActivity(),civ_user_icon,tv_user_name,btn_login);
                if (btn_login.getText().equals("登录")){
                    qqlogin.getPlatform();
                }else {
                    //移除授权
                    qqlogin.getQq().removeAccount(true);
                    tv_user_name.setText("未登录");
                    btn_login.setText("登录");
                    civ_user_icon.setImageResource(R.mipmap.icon_default);
                }
            }
        });
    }

}
