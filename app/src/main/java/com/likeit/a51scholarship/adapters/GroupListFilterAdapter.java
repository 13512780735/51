package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.model.circle_model.GroupListFilterModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class GroupListFilterAdapter extends MyBaseAdapter<GroupListFilterModel>{
    public GroupListFilterAdapter(Context context, List<GroupListFilterModel> groupListFilterModels) {
        super(context, groupListFilterModels);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.group_list_filter_items, parent, false);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GroupListFilterModel mGroupListFilterModel=getItem(position);
        holder.tv_name.setText(mGroupListFilterModel.getTitle());
        return convertView;
    }
    public class ViewHolder{
        private  TextView tv_name;
    }
}
