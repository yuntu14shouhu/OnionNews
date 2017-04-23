package com.onionnews.demo.onionnews.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.onionnews.demo.onionnews.Adapter.TitleRecyclerAdapter;
import com.onionnews.demo.onionnews.DataBean.TabDataBean;
import com.onionnews.demo.onionnews.R;
import com.onionnews.demo.onionnews.Utils.DividerRecyclerViewItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ColumnsManagementActivity extends AppCompatActivity {

    private Button btn_back;
    private RecyclerView rv_showed,rv_added;
    private List<TabDataBean.TListBean> list_dataAdd,list_dataRemove;
    private TitleRecyclerAdapter adapterAdd,adapterRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columns_management);

        initData();
        initView();
        initEvent();
    }

    /**
     * 栏目添加删除操作
     */
    private void initEvent() {
        adapterAdd.setOnItemClickListener(new TitleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapterRemove.addData(list_dataAdd.get(position));
                adapterAdd.removeData(position);
            }
        });
        adapterRemove.setOnItemClickListener(new TitleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapterAdd.addData(list_dataRemove.get(position));
                adapterRemove.removeData(position);
            }
        });
    }

    private void initData() {
        list_dataAdd = new ArrayList<>();
        list_dataRemove = new ArrayList<>();

        Intent intent = getIntent();
        list_dataAdd = (List<TabDataBean.TListBean>) intent.getSerializableExtra("newsClassify");
        list_dataRemove = (List<TabDataBean.TListBean>) intent.getSerializableExtra("newsBackup");
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newsClassify", (Serializable) list_dataAdd);
                intent.putExtra("newsBackup", (Serializable) list_dataRemove);
                setResult(2,intent);
                finish();
            }
        });
        rv_showed = (RecyclerView) findViewById(R.id.rv_showed);
        rv_added = (RecyclerView) findViewById(R.id.rv_added);
        //设置布局管理器
        GridLayoutManager showManager = new GridLayoutManager(this,5);
        rv_showed.setLayoutManager(showManager);
        //设置间距
        rv_showed.addItemDecoration(new DividerRecyclerViewItem(this,5));
        rv_showed.setItemAnimator(new DefaultItemAnimator());

        rv_added.setLayoutManager(new GridLayoutManager(this,5));
        rv_added.addItemDecoration(new DividerRecyclerViewItem(this,5));
        rv_added.setItemAnimator(new DefaultItemAnimator());

        adapterAdd = new TitleRecyclerAdapter(this,list_dataAdd);
        adapterRemove = new TitleRecyclerAdapter(this,list_dataRemove);
        rv_showed.setAdapter(adapterAdd);
        rv_added.setAdapter(adapterRemove);
    }
}
