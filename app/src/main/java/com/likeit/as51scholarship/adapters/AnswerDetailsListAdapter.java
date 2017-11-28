package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.question.AnswerListBean;
import com.likeit.as51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class AnswerDetailsListAdapter extends MyBaseAdapter<AnswerListBean> {
    public AnswerDetailsListAdapter(Context context, List<AnswerListBean> answerListBeen) {
        super(context, answerListBeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.layout_answer_details_listview_items, parent, false);
            holder.img = (CircleImageView) convertView
                    .findViewById(R.id.answers_issue_avatar);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.answers_issue_name);
            holder.tv_time = (TextView) convertView
                    .findViewById(R.id.answers_issue_time);
            holder.tv_floor = (TextView) convertView
                    .findViewById(R.id.tv_floor);
            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.answers_issue_details);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AnswerListBean mAnswerListBean = getItem(position);
        ImageLoader.getInstance().displayImage(mAnswerListBean.getHeadimg(),holder.img);
        holder.tv_name.setText(mAnswerListBean.getAuthor());
        holder.tv_floor.setText("第"+mAnswerListBean.getFloor()+"楼");
        holder.tv_time.setText(mAnswerListBean.getInterval());
        holder.tv_content.setText("回答 "+mAnswerListBean.getContent());
        return convertView;
    }

    public class ViewHolder {
        private CircleImageView img;
        private TextView tv_name,tv_time,tv_content,tv_floor;
    }
}
