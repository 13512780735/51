package com.likeit.as51scholarship.adapters.userapply;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.adapters.MyBaseAdapter;
import com.likeit.as51scholarship.model.userapply.UserDistrictBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class UserApplyDistrictAdapter extends MyBaseAdapter<UserDistrictBean> {
    public UserApplyDistrictAdapter(Context context, List<UserDistrictBean> userDistrictBeen) {
        super(context, userDistrictBeen);
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
        UserDistrictBean userDistrictBeen = getItem(position);
        holder.tv_name.setText(userDistrictBeen.getName());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_name;
    }
}
