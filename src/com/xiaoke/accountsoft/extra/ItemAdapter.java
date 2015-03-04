package com.xiaoke.accountsoft.extra;

import java.util.ArrayList;
import java.util.List;

import com.xiaoke.accountsoft.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<GridItem> items;
	
	public ItemAdapter(String[] titles,int[] images,Context context) {
		super();
		items = new ArrayList<GridItem>();
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < images.length; i++) {
			GridItem gridItem = new GridItem(titles[i], images[i]);
			items.add(gridItem);
		}
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != items) {
			return items.size();
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.gvitem, null);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView)convertView.findViewById(R.id.ItemImage);
			viewHolder.title = (TextView)convertView.findViewById(R.id.ItemTitle);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.title.setText(items.get(position).getTitle());
		viewHolder.image.setImageResource(items.get(position).getImageId());
		
		return convertView;
	}

}
