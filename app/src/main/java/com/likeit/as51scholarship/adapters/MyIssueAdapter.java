package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.MyIssueInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class MyIssueAdapter extends MyBaseAdapter<MyIssueInfo> {
    public MyIssueAdapter(Context context, List<MyIssueInfo> myIssueInfo) {
        super(context, myIssueInfo);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.layout_my_issue_listview_items, parent, false);
            holder.img = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.tv_status = (TextView) convertView
                    .findViewById(R.id.tv_status);
            holder.tv_del = (Button) convertView
                    .findViewById(R.id.tv_del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyIssueInfo mMyIssueInfo = getItem(position);
        ImageLoader.getInstance().displayImage(mMyIssueInfo.getCover(), holder.img);
        holder.tv_title.setText(mMyIssueInfo.getTitle());
        if ("待审核".equals(mMyIssueInfo.getStatus())) {
            holder.tv_status.setText(mMyIssueInfo.getStatus());
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.tv_status.setText(mMyIssueInfo.getStatus());
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.login_btn_bg_color));
        }
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelClickListener.onDelClick(position);
            }
        });
        return convertView;
    }

    public interface onDelClickListener {
        void onDelClick(int i);
    }

    private onDelClickListener mDelClickListener;

    public void setOnDelClickListener(
            onDelClickListener onDelClickListener) {
        this.mDelClickListener = onDelClickListener;
    }

    public class ViewHolder {
        private ImageView img;
        private TextView tv_title, tv_status;
        private Button tv_del;
    }
}
