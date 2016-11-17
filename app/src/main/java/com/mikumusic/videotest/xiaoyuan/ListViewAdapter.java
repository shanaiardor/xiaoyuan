package com.mikumusic.videotest.xiaoyuan;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/6.
 */

public class ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<List_date> videodate;
    @Override
    public int getCount() {
        return videodate.size();
    }

    public ListViewAdapter(Context context , ArrayList<List_date> videodate){
        //上下文 获取的json 数据集合
        this.context = context;
        this.videodate = videodate;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        //如果该行未被回收，填充该行
        if (row == null){
            row = View.inflate(context,R.layout.listview,null);
        } else {
            row = convertView;
        }
//        SmartImageView icon = (SmartImageView) row.findViewById(R.id.list_item_icon);
//        icon.setImageUrl(videodate.get(position).getIcon());
        //videodate.get(position).getIcon()
        TextView title = (TextView) row.findViewById(R.id.list_item_title);
        title.setText(videodate.get(position).getTitle());
        TextView body = (TextView) row.findViewById(R.id.list_item_body);
        body.setText(videodate.get(position).getBody());
        final ImageView imageView = (ImageView) row.findViewById(R.id.list_item_icon);
        DownImage downImage = new DownImage(videodate.get(position).getIcon());
        downImage.loadImage(new DownImage.ImageCallBack() {
            @Override
            public void getDrawable(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });
        return row;
    }

}
