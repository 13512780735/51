package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;


import com.likeit.a51scholarship.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/26.
 */

public class CircleGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Object>> list;
    LayoutInflater layoutInflater;
    private ImageView ivAvatar;
    private Button tvTitle;
    private Button tvTitle01;

    public CircleGridViewAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() + 1;//注意此处
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = layoutInflater.inflate(R.layout.cricle_gridview_items, null);
        ivAvatar = (ImageView) convertView
                .findViewById(R.id.cricle_gridview_iv_Avatar);
        tvTitle = (Button) convertView
                .findViewById(R.id.cricle_gridview_tv_name);
//        tvTitle01 = (Button) convertView
//                .findViewById(R.id.cricle_gridview_tv_name01);
       if (position < list.size()) {
           // ivAvatar.setBackgroundResource(R.mipmap
            tvTitle.setText(list.get(position).get("name").toString());
        } else {
            ivAvatar.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            tvTitle01.setVisibility(View.VISIBLE);
        }
        return convertView;

    }


}
