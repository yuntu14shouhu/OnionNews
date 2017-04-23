package com.onionnews.demo.onionnews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onionnews.demo.onionnews.DataBean.TabDataBean;
import com.onionnews.demo.onionnews.R;
import com.onionnews.demo.onionnews.Utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/10/26.
 */

public class TitleRecyclerAdapter extends RecyclerView.Adapter<TitleRecyclerAdapter.MyViewHolder>{
    private List<TabDataBean.TListBean> mDatas;
    private LayoutInflater mInflater;
    private Context context;
    private List<Integer> mHeights;

    /**
     * TitleRecyclerAdapter 构造器
     * @param context 上下文
     * @param mDatas 数据源
     */
    public TitleRecyclerAdapter(Context context, List<TabDataBean.TListBean> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mHeights = new ArrayList<>();
        for (int i=0; i<mDatas.size(); i++){
            mHeights.add(DensityUtil.dipToPx(context,25));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_column_recycler,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /**
     *
     * @param holder 自定义控件缓存类
     * @param position RecyclerView的item的标号
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
        lp.height = mHeights.get(position);

        holder.tv.setLayoutParams(lp);
        holder.tv.setText(mDatas.get(position).getTname());
        // 如果设置了回调，则设置点击事件
        if (myOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = holder.getLayoutPosition();
                    if (index != -1){
                        myOnItemClickListener.onItemClick(holder.itemView,index);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_titlect);
        }
    }

    //栏目的添加和删除方法
    public void addData(TabDataBean.TListBean data){
        mDatas.add(mDatas.size(),data);
        mHeights.add(DensityUtil.dipToPx(context,25));
        notifyItemInserted(mDatas.size());
    }
    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    //设置监听回调接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    private OnItemClickListener myOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener myOnItemClickListener){
        this.myOnItemClickListener = myOnItemClickListener;
    }
}
