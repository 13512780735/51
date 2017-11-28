package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.CourseListBean;
import com.likeit.as51scholarship.model.question.AnswerListBean;
import com.likeit.as51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class CourseListAdapter extends MyBaseAdapter<CourseListBean>{
    public CourseListAdapter(Context context, List<CourseListBean> courseListBeen) {
        super(context, courseListBeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.course_listview_items, parent, false);
            holder.img = (ImageView) convertView
                    .findViewById(R.id.course_listview_avatar);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.course_listview_name);
            holder.tv_time = (TextView) convertView
                    .findViewById(R.id.course_listview_time);
            holder.tv_free = (Button) convertView
                    .findViewById(R.id.tv_free);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CourseListBean mCourseListBean = getItem(position);
        ImageLoader.getInstance().displayImage(mCourseListBean.getCover(),holder.img);
        holder.tv_name.setText(mCourseListBean.getTitle());
        holder.tv_time.setText(mCourseListBean.getDuration());
        String free=mCourseListBean.getIsfree();
        if("1".equals(free)){
            holder.tv_free.setText("免费");
        }
        return convertView;
    }

    public class ViewHolder {
        private ImageView img;
        private Button tv_free;
        private TextView tv_name,tv_time;
    }
}
