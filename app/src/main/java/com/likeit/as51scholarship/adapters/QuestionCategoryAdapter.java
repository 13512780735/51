package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.question.QuestionCategoryBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class QuestionCategoryAdapter extends MyBaseAdapter<QuestionCategoryBean> {
    public QuestionCategoryAdapter(Context context, List<QuestionCategoryBean> questionCategoryBeen) {
        super(context, questionCategoryBeen);
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
        QuestionCategoryBean userDistrictBeen = getItem(position);
        holder.tv_name.setText(userDistrictBeen.getTitle());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_name;
    }
}
