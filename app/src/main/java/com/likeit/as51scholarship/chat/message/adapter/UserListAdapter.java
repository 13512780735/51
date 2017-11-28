package com.likeit.as51scholarship.chat.message.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.adapters.AnswerDetailsListAdapter;
import com.likeit.as51scholarship.adapters.MyBaseAdapter;
import com.likeit.as51scholarship.chat.message.model.EaseUserBean;
import com.likeit.as51scholarship.model.question.AnswerListBean;
import com.likeit.as51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class UserListAdapter extends MyBaseAdapter<EaseUserBean> {
    public UserListAdapter(Context context, List<EaseUserBean> easeUserBeen) {
        super(context, easeUserBeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.layout_find_userlist_items, parent, false);
            holder.img = (CircleImageView) convertView
                    .findViewById(R.id.listview_iv_avatar);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.listview_tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EaseUserBean mEaseUserBean = getItem(position);
        ImageLoader.getInstance().displayImage(mEaseUserBean.getHeadimg(),holder.img);
        holder.tv_name.setText(mEaseUserBean.getNickname());
        return convertView;
    }

    public class ViewHolder {
        private CircleImageView img;
        private TextView tv_name;
    }
}
