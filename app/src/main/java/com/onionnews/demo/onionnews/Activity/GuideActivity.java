package com.onionnews.demo.onionnews.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.onionnews.demo.onionnews.Adapter.GuideViewPageAdapter;
import com.onionnews.demo.onionnews.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    private Button btn_jump;
    private ImageView[] iv_gudies = new ImageView[3];
    private ViewPager vp_guide;
    private ArrayList<View> list_view;
    private GuideViewPageAdapter gvpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }
    private void initView() {
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        list_view = new ArrayList<>();
        list_view.add(LayoutInflater.from(this).inflate(R.layout.guide_page1,null));
        list_view.add(LayoutInflater.from(this).inflate(R.layout.guide_page2,null));
        list_view.add(LayoutInflater.from(this).inflate(R.layout.guide_page3,null));
        gvpAdapter = new GuideViewPageAdapter(list_view);
        vp_guide.setAdapter(gvpAdapter);
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initIm();
                iv_gudies[position].setImageResource(R.mipmap.style_after);
                if (position==2){
                    btn_jump.setText("进入阅读");
                }else {
                    btn_jump.setText("跳过");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        iv_gudies[0] = (ImageView) findViewById(R.id.iv_guide1);
        iv_gudies[1] = (ImageView) findViewById(R.id.iv_guide2);
        iv_gudies[2] = (ImageView) findViewById(R.id.iv_guide3);
        btn_jump = (Button) findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,NewsActivity.class));
                finish();
            }
        });

    }

    private void initIm() {
        for (int i=0 ;i<3 ; i++){
            iv_gudies[i].setImageResource(R.mipmap.style_before);
        }
    }
}
