package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.app.MyApplication;
import com.likeit.as51scholarship.model.circle_model.CircleEssayModel;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.likeit.as51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class CircleDetailsEssayAdapter extends MyBaseAdapter<CircleEssayModel> {
    public CircleDetailsEssayAdapter(Context context, List<CircleEssayModel> circleEssayModels) {
        super(context, circleEssayModels);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.circle_details_essay_list_items, parent, false);
            holder.tvEssayName = (TextView) convertView
                    .findViewById(R.id.circle_essay_name);
            holder.ivAvatar = (CircleImageView) convertView
                    .findViewById(R.id.circle_essay_avatar);
            holder.tvEssayTime = (TextView) convertView
                    .findViewById(R.id.circle_essay_time);
            holder.tvAdd = (TextView) convertView
                    .findViewById(R.id.tv_add_friend);
            holder.tvTitle = (TextView) convertView
                    .findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CircleEssayModel data1 = getItem(position);
        holder.tvEssayName.setText(data1.getUserinfo().getNickname());
        holder.tvEssayTime.setText(data1.getPost_time());
        ImageLoader.getInstance().displayImage(data1.getUserinfo().getHeadimg(), holder.ivAvatar);
        holder.tvTitle.setText(data1.getTitle());
        String easemob_id = UtilPreference.getStringValue(MyApplication.mContext, "easemob_id");
        if (StringUtil.isBlank(data1.getUserinfo().getEasemob_id())) {
            holder.tvAdd.setVisibility(View.GONE);
        } else if ("1".equals(data1.getUserinfo().getIsfriend())) {
            holder.tvAdd.setText("已添加");
            holder.tvAdd.setClickable(false);
        }
        else if (easemob_id.equals(data1.getUserinfo().getEasemob_id())) {
            holder.tvAdd.setVisibility(View.GONE);
        }
        else{
            holder.tvAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnAddClickListener.onAddClick(position);

                }
            });
        }
        return convertView;
    }

    /**
     * 点击添加的时候
     */
    public interface onAddClickListener {
        void onAddClick(int i);
    }

    private onAddClickListener mOnAddClickListener;

    public void setOnAddClickListener(
            onAddClickListener mOnAddClickListener) {
        this.mOnAddClickListener = mOnAddClickListener;
    }

    private class ViewHolder {
        CircleImageView ivAvatar;
        TextView tvEssayTime, tvAdd, tvTitle, tvEssayName;

    }
}
