package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.Messageabean;
import com.likeit.as51scholarship.utils.richtext.RichText;
import com.likeit.as51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class MessageListAdapter extends MyBaseAdapter<Messageabean> {
    public MessageListAdapter(Context context, List<Messageabean> messageabeen) {
        super(context, messageabeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.layout_message_listview_items, parent, false);
            holder.img = (CircleImageView) convertView
                    .findViewById(R.id.iv_avatar);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.tv_time = (TextView) convertView
                    .findViewById(R.id.tv_time);
            holder.tv_content = (RichText) convertView
                    .findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Messageabean mMessageabean = getItem(position);
        // ImageLoader.getInstance().displayImage(mMessageabean.getLink(),holder.img);
        holder.img.setImageResource(R.drawable.icon_message);
        holder.tv_name.setText(mMessageabean.getTitle());
        holder.tv_time.setText(mMessageabean.getCreate_time());
        holder.tv_content.setRichText(mMessageabean.getContent());
        return convertView;
    }

    public class ViewHolder {
        private CircleImageView img;
        private TextView tv_name, tv_time;
        private RichText tv_content;
    }
}
