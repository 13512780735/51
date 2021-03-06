package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.SchoolAttributeVo;
import com.likeit.as51scholarship.view.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class SchoolFilterAttrAdapter extends BaseAdapter {
    private Context context;
    private List<SchoolAttributeVo> data = new ArrayList<SchoolAttributeVo>();
   // private int selectPosition = -1;//用于记录用户选择的变量
    private int clickTemp = -1;
    public SchoolFilterAttrAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            EventBus.getDefault().post(new MessageEvent(data.toString()));
        } else {
            myView.attr.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
            myView.attr.setTextColor(Color.DKGRAY);
            data.get(position).setChecked(false);
            EventBus.getDefault().post(new MessageEvent(data.toString()));
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
