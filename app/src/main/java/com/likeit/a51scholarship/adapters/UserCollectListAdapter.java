package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.model.UserCollectBean;
import com.likeit.a51scholarship.model.userapply.UserDistrictBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class UserCollectListAdapter extends MyBaseAdapter<UserCollectBean> {
    public UserCollectListAdapter(Context context, List<UserCollectBean> userCollectBeen) {
        super(context, userCollectBeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.collect_listview_items, parent, false);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserCollectBean userCollectBean = getItem(position);
        holder.tv_name.setText(userCollectBean.getName());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_name;
    }
}
