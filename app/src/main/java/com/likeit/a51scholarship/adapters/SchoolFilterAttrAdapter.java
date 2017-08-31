package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.model.SchoolAttributeNameVo;
import com.likeit.a51scholarship.model.SchoolAttributeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class SchoolFilterAttrAdapter extends BaseAdapter {
    private Context context;
    private List<SchoolAttributeVo> data = new ArrayList<SchoolAttributeVo>();

    public SchoolFilterAttrAdapter(Context context) {
        this.context = context;
    }



    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
    public View getView(final int position, View v, ViewGroup parent) {
        final MyView myView;
        if (v == null) {
            myView = new MyView();
            v = View.inflate(context, R.layout.school_filter_gridview_items, null);
            myView.attr = (TextView) v.findViewById(R.id.attr_name);
            v.setTag(myView);
        } else {
            myView = (MyView) v.getTag();
        }
        myView.attr.setText(data.get(position).getAttr_name());
        /**
         * 根据选中状态来设置item的背景和字体颜色
         */
        if (data.get(position).isChecked()) {
            myView.attr.setBackgroundResource(R.drawable.goods_attr_selected_shape);
            data.get(position).setChecked(true);
            myView.attr.setTextColor(Color.WHITE);
        } else {
            myView.attr.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
            myView.attr.setTextColor(Color.DKGRAY);
            data.get(position).setChecked(false);
        }
        return v;

    }

    static class MyView {
        public TextView attr;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(boolean isUnfold,
                                     final List<SchoolAttributeVo> tempData) {
        if (tempData == null || 0 == tempData.size()) {
            return;
        }
        data.clear();
        data.addAll(tempData);
        notifyDataSetChanged();
    }
}
