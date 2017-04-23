package com.onionnews.demo.onionnews.Adapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.onionnews.demo.onionnews.DataBean.NewsDataBean;
import com.onionnews.demo.onionnews.R;
import com.onionnews.demo.onionnews.Utils.AnimateFirstDisplayListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义适配器绑定数据
 *
 */
public class NewsFragmentPullListAdapter extends BaseAdapter {
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private List<NewsDataBean> list_news;
	Context context;

	public void setList_news(List<NewsDataBean> list_news) {
		this.list_news = list_news;
		notifyDataSetChanged();
	}

	/**
	 *写构造方法是为了实力化对象的时候1传递数据2提供context参数 
	 *
	 */

	public NewsFragmentPullListAdapter(Context context, ArrayList<NewsDataBean> list_news) {
		setList_news(list_news);
		this.context = context;
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.mipmap.icon_default)//设置图片在下载期间显示的图片
		.showImageForEmptyUri(R.mipmap.defaul_fail)//设置图片Uri为空或是错误的时候显示的图片
		 .showImageOnFail(R.mipmap.defaul_fail)//设置图片加载/解码过程中错误时候显示的图片
		 .cacheInMemory(true)//是否緩存都內存中
		 .cacheOnDisc(true)//是否緩存到sd卡上
		 .displayer(new RoundedBitmapDisplayer(3))//设置图片圆角
				.build();
	}

	@Override
	public int getCount() {
	
		return list_news.size();
	}

	@Override
	public NewsDataBean getItem(int position) {
		
		return list_news.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_news_viewpager, null);
			holder = new ViewHolder();
			holder.iv_item_icon = (ImageView) convertView.findViewById(R.id.iv_item_icon);
			holder.tv_item_title = (TextView) convertView.findViewById(R.id.tv_item_title);
			holder.tv_item_source = (TextView) convertView.findViewById(R.id.tv_item_source);
			holder.tv_item_replyCount = (TextView) convertView.findViewById(R.id.tv_item_replyCount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//数据源中position对应的对象
		NewsDataBean datas = list_news.get(position);
		holder.tv_item_title.setText(datas.getTitle());
		holder.tv_item_source.setText(datas.getSource());
		holder.tv_item_replyCount.setText("评论:"+datas.getReplyCount());
		// ImageLoader
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(datas.getImgsrc(), holder.iv_item_icon, options, animateFirstListener);
		return convertView;
	}
	private class ViewHolder {
		ImageView iv_item_icon;
		TextView tv_item_title;
		TextView tv_item_source;
		TextView tv_item_replyCount;
	}

}
