package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.circle_model.GroupListModel;
import com.likeit.as51scholarship.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CricleListViewAdapter extends MyBaseAdapter<GroupListModel> {
    public CricleListViewAdapter(Context context, List<GroupListModel> groupListViewBeen) {
        super(context, groupListViewBeen);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.circle_listview_items, parent, false);
            holder.ivAvatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            holder.tvSchoolName = (TextView) convertView
                    .findViewById(R.id.tv_school_name);
            holder.tvSchoolNumber = (TextView) convertView
                    .findViewById(R.id.tv_school_number);
            holder.tvSchoolTopic = (TextView) convertView
                    .findViewById(R.id.tv_school_topic);
            holder.btnEnFollow = (Button) convertView
                    .findViewById(R.id.button01);
            holder.btnFollow = (Button) convertView
                    .findViewById(R.id.button02);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GroupListModel data= getItem(position);
        //  ImageLoader.getInstance().displayImage(data1.getImg(), holder.school_listview_iv_school);
        String imgUrl=data.getLogo();
        if(StringUtil.isBlank(imgUrl)){
            holder.ivAvatar.setImageResource(R.mipmap.icon_01_3x);
        }else{
            ImageLoader.getInstance().displayImage(imgUrl, holder.ivAvatar);
           // holder.ivAvatar.setImageResource(R.mipmap.icon_01_3x);
        }
        holder.tvSchoolName.setText(data.getTitle());
        holder.tvSchoolNumber.setText("成员："+data.getMember_num());
        holder.tvSchoolTopic.setText("帖子："+data.getPost_num());
        String isfollow=data.getIsfollow();
        if("1".equals(isfollow)){
            //已关注
            holder.btnFollow.setVisibility(View.VISIBLE);
            holder.btnEnFollow.setVisibility(View.INVISIBLE);
        }else{
            //未关注
            holder.btnEnFollow.setVisibility(View.VISIBLE);
            holder.btnFollow.setVisibility(View.INVISIBLE);
        }
        // 点击关注
        final ViewHolder finalHolder = holder;
        holder.btnFollow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnBtnFollowClickListener.onFollowClick(position);
                finalHolder.btnFollow.setVisibility(View.INVISIBLE);
                finalHolder.btnEnFollow.setVisibility(View.VISIBLE);

            }
        });
        // 点击未关注
        holder.btnEnFollow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnBtnEnFollowClickListener.onEnFollowClick(position);
                finalHolder.btnEnFollow.setVisibility(View.INVISIBLE);
                finalHolder.btnFollow.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivAvatar;
        private TextView tvSchoolName;
        private TextView tvSchoolNumber;
        private TextView tvSchoolTopic;
        private Button btnFollow;//关注
        private Button btnEnFollow;//未关注

    }

    /**
     * 点击关注的时候
     */
    public interface onBtnFollowClickListener {
        void onFollowClick(int i);
    }

    private onBtnFollowClickListener mOnBtnFollowClickListener;

    public void setOnFollowClickListener(
            onBtnFollowClickListener mOnBtnFollowClickListener) {
        this.mOnBtnFollowClickListener = mOnBtnFollowClickListener;
    }
    /**
     * 点击未关注的时候
     */
    public interface onBtnEnFollowClickListener {
        void onEnFollowClick(int i);
    }

    private onBtnEnFollowClickListener mOnBtnEnFollowClickListener;

    public void setOnEnFollowClickListener(
            onBtnEnFollowClickListener mOnBtnEnFollowClickListener) {
        this.mOnBtnEnFollowClickListener = mOnBtnEnFollowClickListener;
    }
}
