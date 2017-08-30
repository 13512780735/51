package com.likeit.a51scholarship.adapters.userapply;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.MyBaseAdapter;
import com.likeit.a51scholarship.model.schoolapply.PlanTimeBean;
import com.likeit.a51scholarship.model.userapply.UserPlanTimeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class UserApplyPlanTimeAdapter extends MyBaseAdapter<UserPlanTimeBean> {
    public UserApplyPlanTimeAdapter(Context context, List<UserPlanTimeBean> userPlanTimeBeen) {
        super(context, userPlanTimeBeen);
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
        UserPlanTimeBean userPlanTimeBeen = getItem(position);
        holder.tv_name.setText(userPlanTimeBeen.getName());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_name;
    }
}
