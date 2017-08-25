package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.model.HomeItemNewsBean;

import java.util.List;

/**
 * Created by Administrator on 2017\7\22 0022.
 */

public class HomeItemNewsAdapter extends MyBaseAdapter<HomeItemNewsBean> {
    public HomeItemNewsAdapter(Context context, List<HomeItemNewsBean> homeItemNewsBeen) {
        super(context, homeItemNewsBeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.news_listview_items, parent, false);
            holder.new_author = (TextView) convertView
                    .findViewById(R.id.new_author);
            holder.new_name = (TextView) convertView
                    .findViewById(R.id.new_name);
            holder.news_read_number = (TextView) convertView
                    .findViewById(R.id.news_read_number);
            holder.news_read_time = (TextView) convertView
                    .findViewById(R.id.news_read_time);
            holder.news_comment_number = (TextView) convertView
                    .findViewById(R.id.news_comment_number);
            holder.new_share = (ImageView) convertView
                    .findViewById(R.id.new_share);
            holder.imageView_avatar = (ImageView) convertView
                    .findViewById(R.id.imageView_avatar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeItemNewsBean data1 = getItem(position);
        holder.new_author.setText(data1.getAuthor());
        holder.new_name.setText(data1.getTitle());
        holder.news_read_number.setText("阅读" + data1.getView());
        holder.news_read_time.setText(data1.getInterval());
        holder.imageView_avatar.setImageResource(R.mipmap.test02);
        if ("0".equals(data1.getComment().toString())) {
            holder.news_comment_number.setVisibility(View.GONE);
            holder.new_share.setVisibility(View.GONE);
        } else {
            holder.news_comment_number.setVisibility(View.VISIBLE);
            holder.new_share.setVisibility(View.VISIBLE);
            holder.news_comment_number.setText(data1.getComment());
            holder.new_share.setImageResource(R.mipmap.icon_more);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView new_author, new_name, news_read_number, news_read_time, news_comment_number;
        ImageView imageView_avatar, new_share;

    }
}
