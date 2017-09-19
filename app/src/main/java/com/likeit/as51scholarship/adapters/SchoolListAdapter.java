package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.SchoolListBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SchoolListAdapter extends MyBaseAdapter<SchoolListBean>{
    public SchoolListAdapter(Context context, List<SchoolListBean> schoolListBeen) {
        super(context, schoolListBeen);
    }
    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.school_listview_items, parent, false);
            holder.school_listview_iv_school = (ImageView) convertView
                    .findViewById(R.id.school_listview_iv_school);
            holder.school_listview_tv_Chinese_name = (TextView) convertView
                    .findViewById(R.id.school_listview_tv_Chinese_name);
            holder.school_listview_tv_English_name = (TextView) convertView
                    .findViewById(R.id.school_listview_tv_English_name);
            holder.school_listview_tv_rank = (TextView) convertView
                    .findViewById(R.id.school_listview_tv_rank);
            holder.school_listview_tv_rate = (TextView) convertView
                    .findViewById(R.id.school_listview_tv_rate);
            holder.school_listview_tv_scholarship = (TextView) convertView
                    .findViewById(R.id.school_listview_tv_scholarship);
            holder.school_listview_bt_apply = (Button) convertView
                    .findViewById(R.id.school_listview_bt_apply);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SchoolListBean data1 = getItem(position);
        holder.school_listview_tv_Chinese_name.setText(data1.getName());
        holder.school_listview_tv_English_name.setText(data1.getEn_name());
        holder.school_listview_tv_rank.setText(data1.getRanking());
        holder.school_listview_tv_rate.setText(data1.getRate());
        holder.school_listview_tv_scholarship.setText(data1.getScholarship());
        ImageLoader.getInstance().displayImage(data1.getImg(), holder.school_listview_iv_school);
        // 订单详情点击事件
        holder.school_listview_bt_apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mSchoolApplyClickListener.onApplyClick(position);

            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView school_listview_tv_Chinese_name, school_listview_tv_English_name, school_listview_tv_rank,school_listview_tv_rate,school_listview_tv_scholarship;
        ImageView school_listview_iv_school;
        Button school_listview_bt_apply;

    }
    /**
     * 点击订单号
     */
    public interface onSchoolApplyClickListener {
        void onApplyClick(int i);
    }

    private onSchoolApplyClickListener mSchoolApplyClickListener;

    public void setOnApplyClickListener(
            onSchoolApplyClickListener mSchoolApplyClickListener) {
        this.mSchoolApplyClickListener = mSchoolApplyClickListener;
    }

}
