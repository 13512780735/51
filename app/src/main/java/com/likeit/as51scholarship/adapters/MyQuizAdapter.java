package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.MyQuizInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class MyQuizAdapter extends MyBaseAdapter<MyQuizInfo> {
    public MyQuizAdapter(Context context, List<MyQuizInfo> myQuizInfo) {
        super(context, myQuizInfo);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.layout_my_quiz_listview_items, parent, false);
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.question_quiz_live01 = (TextView) convertView
                    .findViewById(R.id.question_quiz_live01);
            holder.question_quiz_live02 = (TextView) convertView
                    .findViewById(R.id.question_quiz_live02);
            holder.tv_del = (Button) convertView
                    .findViewById(R.id.tv_del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyQuizInfo mMyQuizInfo = getItem(position);
        holder.tv_title.setText(mMyQuizInfo.getContent());
        holder.question_quiz_live01.setText(mMyQuizInfo.getCategory());
        holder.question_quiz_live02.setText(mMyQuizInfo.getCountry());

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
        private TextView tv_title, question_quiz_live01, question_quiz_live02;
        private Button tv_del;
    }
}