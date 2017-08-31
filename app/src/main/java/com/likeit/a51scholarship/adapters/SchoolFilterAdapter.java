package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.model.SchoolAttributeNameVo;
import com.likeit.a51scholarship.view.GridViewForScrollView;
import com.likeit.a51scholarship.view.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class SchoolFilterAdapter extends BaseAdapter {
    private Context context;
    private List<SchoolAttributeNameVo> data;
    final int VIEW_TYPE = 9;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;
    final int TYPE_4 = 3;
    final int TYPE_5 = 4;
    final int TYPE_6 = 5;
    final int TYPE_7 = 6;
    final int TYPE_8 = 7;
    final int TYPE_9 = 8;
    private MyView myView;
    private MyView2 myView2;


    public SchoolFilterAdapter(Context context, List<SchoolAttributeNameVo> data) {
        this.context = context;
        this.data = data;
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

    //每个convert view 都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {

        int p = position % 10;

        if (p == 0) {
            return TYPE_1;
        } else if (p == 1) {
            return TYPE_2;
        } else if (p == 2) {
            return TYPE_3;
        } else if (p == 3) {
            return TYPE_4;
        } else if (p == 4) {
            return TYPE_5;
        } else if (p == 5) {
            return TYPE_6;
        } else if (p == 6) {
            return TYPE_7;
        } else if (p == 7) {
            return TYPE_8;
        } else if (p == 8) {
            return TYPE_9;
        } else {
            return TYPE_1;
        }
    }

    @Override
    public int getViewTypeCount() {

        return VIEW_TYPE;

    }
    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        int type = getItemViewType(position);
        if (v == null) {
            switch (type) {
                case TYPE_1:
                case TYPE_6:
                case TYPE_7:
                    myView = new MyView();
                    v = View.inflate(context, R.layout.school_filter_listview_item01, null);
                    myView.name = (TextView) v.findViewById(R.id.school_filter_items_tvName);
                    myView.grid = (GridViewForScrollView) v.findViewById(R.id.school_filter_items_GridView);
                    myView.grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    v.setTag(myView);
                    break;
                case TYPE_2:
                case TYPE_9:
                    myView = new MyView();
                    v = View.inflate(context, R.layout.school_filter_listview_item02, null);
                    myView.name = (TextView) v.findViewById(R.id.school_filter_items_tvName);
                    myView.grid = (GridViewForScrollView) v.findViewById(R.id.school_filter_items_GridView);
                    myView.grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    v.setTag(myView);
                    break;
                case TYPE_3:
                    myView2 = new MyView2();
                    v = View.inflate(context, R.layout.school_filter_listview_item04, null);
                    myView2.name = (TextView) v.findViewById(R.id.school_filter_items_tvName);
                    myView2.tvContent = (TextView) v.findViewById(R.id.school_filter_items_tvContent);
                    v.setTag(myView2);
                    break;
                case TYPE_4:
                case TYPE_5:
                case TYPE_8:
                    myView = new MyView();
                    v = View.inflate(context, R.layout.school_filter_listview_item03, null);
                    myView.name = (TextView) v.findViewById(R.id.school_filter_items_tvName);
                    myView.grid = (GridViewForScrollView) v.findViewById(R.id.school_filter_items_GridView);
                    myView.grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    v.setTag(myView);
                    break;
            }

        } else {
            switch (type) {
                case TYPE_1:
                case TYPE_4:
                case TYPE_5:
                case TYPE_6:
                case TYPE_7:
                case TYPE_8:
                case TYPE_2:
                case TYPE_9:
                    myView = (MyView) v.getTag();
                    break;
                case TYPE_3:
                    myView2 = (MyView2) v.getTag();
                    break;
            }
        }
        switch (type) {
            case TYPE_1:
            case TYPE_4:
            case TYPE_5:
            case TYPE_6:
            case TYPE_7:
            case TYPE_8:
            case TYPE_2:
            case TYPE_9:
                myView.name.setText(data.get(position).getName());
                final SchoolFilterAttrAdapter adapter = new SchoolFilterAttrAdapter(context);
                myView.grid.setAdapter(adapter);
                adapter.notifyDataSetChanged(data.get(position).isNameIsChecked(), data.get(position).getValues());
                myView.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        //设置当前选中的位置的状态为非。
                        data.get(position).getValues().get(arg2).setChecked(!data.get(position).getValues().get(arg2).isChecked());
                        for (int i = 0; i < data.get(position).getValues().size(); i++) {
                            //跳过已设置的选中的位置的状态
                            if (i == arg2) {
                                continue;
                            }
                            data.get(position).getValues().get(i).setChecked(false);
                        }
                        String attriId=data.get(position).getValues().get(arg2).getAttr_id();
                        String attriName=data.get(position).getName();
                        EventBus.getDefault().post(attriId);
                        EventBus.getDefault().post(new MessageEvent(attriId));
                        adapter.notifyDataSetChanged(!data.get(position).isNameIsChecked(), data.get(position).getValues());

                    }
                });
                break;
            case TYPE_3:
                myView2.name.setText(data.get(position).getName());
                break;

        }
        return v;
    }

    static class MyView {
        public TextView name;
        public GridViewForScrollView grid;
    }

    static class MyView2 {
        public TextView name, tvContent;
    }
}
