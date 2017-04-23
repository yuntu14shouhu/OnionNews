package com.onionnews.demo.onionnews.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onionnews.demo.onionnews.Adapter.NewsFragmentPagerAdpter;
import com.onionnews.demo.onionnews.ColumnView.ColumnHorizontalScrollView;
import com.onionnews.demo.onionnews.DataBean.TabDataBean;
import com.onionnews.demo.onionnews.Fragment.DrawerLayoutFragment;
import com.onionnews.demo.onionnews.Fragment.NewsFragment;
import com.onionnews.demo.onionnews.R;
import com.onionnews.demo.onionnews.Utils.AnimateFirstDisplayListener;
import com.onionnews.demo.onionnews.Utils.BaseTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;


public class NewsActivity extends AppCompatActivity {
//引导页
    private SharedPreferences sf;
    private String name = "Lead";
//工具栏--ToolBar和侧滑菜单定义部分
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView tv_toolbar;

//新闻体定义部分
    //自定义的视图
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    LinearLayout mRadioGroup_content;
    LinearLayout ll_more_columns;
    RelativeLayout rl_column;
    private ViewPager mViewPager;
    //新闻的分类列表
    private TabDataBean tabDataBean;
    private List<TabDataBean.TListBean> newsClassify = new ArrayList<>();
    private List<TabDataBean.TListBean> newsBackup = new ArrayList<>();
    //当前选中的栏目
    private int columnSelectIndex = 0;
    //左阴影部分
    private ImageView shade_left;
    //右阴影部分
    private ImageView shade_right;
    //屏幕的宽度
    private int mScreenWidth = 0;
    private ArrayList<Fragment> fragments ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //引导页
        sf = getSharedPreferences(name,MODE_PRIVATE);
        if (getlead()){
            startActivity(new Intent(NewsActivity.this,GuideActivity.class));
            finish();
            saveLead();
        }
        setContentView(R.layout.activity_mian_news);
        //获取屏幕宽度
        mScreenWidth = BaseTools.getWindowsWidth(this);
    //工具栏--ToolBar和侧滑菜单部分
        ShareSDK.initSDK(this);
        initBar();
    //新闻体部分
        initNews();
    }
//引导页部分
public void saveLead(){
    SharedPreferences.Editor sfEditor = sf.edit();
    if (getlead()){
        sfEditor.putBoolean("lead_st",false);
    }
    sfEditor.commit();
}
    public boolean getlead(){
        boolean isLead_st = sf.getBoolean("lead_st",true);
        return isLead_st;
    }
//工具栏Toolbar代码实现部分
    private void initBar() {
        /**
         * 工具栏Toolbar初始化
         * App Logo--mToolBar.setLogo(R.mipmap.XXX);
         * 主标题 默认是app_label的名字
         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar_t);
        mToolbar.setTitle("");
        tv_toolbar = (TextView) findViewById(R.id.toolbar_tv);
        setSupportActionBar(mToolbar);//取代原本的Action Bar

        /**
         * 抽屉布局DrawerLayout初始化
         */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_dl);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        DrawerLayoutFragment fragment = new DrawerLayoutFragment();
        transaction.replace(R.id.drawer_fragment_fl,fragment).commit();
        //使用抽屉开关DrawerToggle作为监听器
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar,0,0);
        drawerToggle.syncState();//设置侧滑动画
        drawerLayout.addDrawerListener(drawerToggle);
        //设置监听--抽屉布局的侧滑动作
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //当菜单滑出后获取焦点，使fragment页面失去焦点不能被点击
                drawerView.setClickable(true);
                tv_toolbar.setText("登录");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                tv_toolbar.setText("洋葱新闻");
            }
        });
    }

//新闻代码实现部分
    private void initNews() {

        mColumnHorizontalScrollView = (ColumnHorizontalScrollView)
                findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout)
                findViewById(R.id.mRadioGroup_content);
        ll_more_columns = (LinearLayout) findViewById(R.id.ll_more_colums);
        rl_column = (RelativeLayout) findViewById(R.id.rl_colum);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.addOnPageChangeListener(pageListener);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        ll_more_columns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,ColumnsManagementActivity.class);
                intent.putExtra("newsClassify", (Serializable) newsClassify);
                intent.putExtra("newsBackup", (Serializable) newsBackup);
                startActivityForResult(intent,1);
            }
        });
        setChangeView(newsClassify,newsBackup,true);
    }

    /**
     * 接收栏目修改结果
     * @param requestCode 请求的匹配碼
     * @param resultCode 结果的匹配碼
     * @param data 返回的意图
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==2){
            newsClassify = (List<TabDataBean.TListBean>) data.getSerializableExtra("newsClassify");
            newsBackup = (List<TabDataBean.TListBean>) data.getSerializableExtra("newsBackup");
            setChangeView(newsClassify,newsBackup,false);
        }
    }

    /**
     * 当栏目发生变化时调用
     */
    private void setChangeView(List<TabDataBean.TListBean> list1,
                               List<TabDataBean.TListBean> list2,boolean isfirst){
        initColumnData(list1,list2,isfirst);
        initTabColumn();
        initFragment();
    }
    /**
     * 获取Column栏目数据
     */
    private void initColumnData(
            List<TabDataBean.TListBean> list1,List<TabDataBean.TListBean> list2,boolean isfirst){
        InputStream is = null;
        try {
            is = getAssets().open("default_columns.txt");
            Reader reader = new InputStreamReader(is);
            Gson gson = new Gson();
            tabDataBean  =  gson.fromJson(reader, TabDataBean.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (isfirst){
         for (int i=0; i<tabDataBean.getTList().size(); i++) {
             if (i<9){
                 newsClassify.add(tabDataBean.getTList().get(i));
             }else {
                 newsBackup.add(tabDataBean.getTList().get(i));
             }
         }
        }else {
            newsClassify = list1;
            newsBackup = list2;
        }
    }
    /**
     * 初始化栏目项
     */
    private void initTabColumn()
    {
        mRadioGroup_content.removeAllViews();
        int count = newsClassify.size();
        mColumnHorizontalScrollView.setParam(this,mScreenWidth,mRadioGroup_content,
                shade_left,shade_right,ll_more_columns,rl_column);
        for (int i = 0;i<count;i++)
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            //创建TextView对象，并对其进行属性设置
            TextView localTextView = new TextView(this);
            localTextView.setTextAppearance(this,R.style.top_category_scroll_view_item_text);

            localTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            localTextView.setGravity(Gravity.CENTER);
            localTextView.setPadding(5,5,5,5);
            localTextView.setId(i);
            localTextView.setText(newsClassify.get(i).getTname());
            localTextView.setTextColor(getResources().getColorStateList(
                    R.color.top_category_scroll_text_color_day
            ));
            //给TextView设置选中状态
            if (columnSelectIndex == i)
            {
                localTextView.setSelected(true);
            }
            //添加监听
            localTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0;i<mRadioGroup_content.getChildCount();i++)
                    {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                        {
                            localView.setSelected(false);
                        }else
                        {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }

                }
            });
            mRadioGroup_content.addView(localTextView,i,params);
        }
    }
    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_position)
    {
        columnSelectIndex = tab_position;
        for (int i =0 ;i<mRadioGroup_content.getChildCount();i++)
        {
            //拿到被选中的视图
            View checkView = mRadioGroup_content.getChildAt(tab_position);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l+k/2-mScreenWidth/2;
            //滚动View
            mColumnHorizontalScrollView.smoothScrollTo(i2,0);
        }
        //判断是否选中
        for (int j = 0;j<mRadioGroup_content.getChildCount();j++)
        {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean isCheck;
            if (j == tab_position)
            {
                isCheck = true;
            }else {
                isCheck = false;
            }
            checkView.setSelected(isCheck);
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment()
    {
        fragments = new ArrayList<>();
        int count = newsClassify.size();
        for (int i = 0;i<count;i++)
        {
            Bundle data = new Bundle();
            data.putString("classify",newsClassify.get(i).getTid());
            //创建Fragment对象
            NewsFragment newFragment = new NewsFragment();
            newFragment.setArguments(data);
            fragments.add(newFragment);
        }
        //创建Fragment适配器
        NewsFragmentPagerAdpter mAdapter = new NewsFragmentPagerAdpter(
                getSupportFragmentManager(),fragments
        );
        mViewPager.setAdapter(mAdapter);

    }

    /**
     * ViewPager切换监听方法
     */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }
        @Override
        public void onPageSelected(int i) {
            //设置选中的View
            mViewPager.setCurrentItem(i);
            selectTab(i);
        }
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    /**
     * 固定的直接抄
     *
     */
    @Override
    public void onBackPressed() {
        AnimateFirstDisplayListener.displayedImages.clear();
        super.onBackPressed();
    }
}
