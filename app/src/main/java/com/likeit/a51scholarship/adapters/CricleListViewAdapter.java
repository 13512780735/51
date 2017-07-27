package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.likeit.a51scholarship.model.CircleListViewBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CricleListViewAdapter extends MyBaseAdapter<CircleListViewBean>{
    public CricleListViewAdapter(Context context, List<CircleListViewBean> circleListViewBeen) {
        super(context, circleListViewBeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
