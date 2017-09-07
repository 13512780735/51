package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.model.circle_model.CircleMemberModel;
import com.likeit.a51scholarship.utils.richtext.RichText;
import com.likeit.a51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class CircleDetailsMemberAdapter extends MyBaseAdapter<CircleMemberModel>{
    public CircleDetailsMemberAdapter(Context context, List<CircleMemberModel> circleMemberModels) {
        super(context, circleMemberModels);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.circle_member_listview_items, parent, false);
            holder.tvEssayName = (TextView) convertView
                    .findViewById(R.id.circle_member_name);
            holder.ivAvatar = (CircleImageView) convertView
                    .findViewById(R.id.circle_member_avatar);
            holder.tvAdd = (TextView) convertView
                    .findViewById(R.id.tv_add_friend);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CircleMemberModel data1 = getItem(position);
        holder.tvEssayName.setText(data1.getNickname());
        ImageLoader.getInstance().displayImage(data1.getHeadimg(),holder.ivAvatar);
        return convertView;
    }

    private class ViewHolder {
        CircleImageView ivAvatar;
        TextView tvAdd,tvEssayName;
        RichText rtContent;

    }
}
