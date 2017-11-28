package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.question.QuestionListBean;
import com.likeit.as51scholarship.model.schoolapply.AreaBean;
import com.likeit.as51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class QuestionListAdapter extends MyBaseAdapter<QuestionListBean> {
    public QuestionListAdapter(Context context, List<QuestionListBean> questionListBeen) {
        super(context, questionListBeen);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.answer_issue_listview_item, parent, false);
            holder.img = (CircleImageView) convertView
                    .findViewById(R.id.answers_issue_avatar);
            holder.name = (TextView) convertView.findViewById(R.id.answers_issue_name);
            holder.time = (TextView) convertView.findViewById(R.id.answers_issue_time);
            holder.live01 = (TextView) convertView.findViewById(R.id.answers_issue_live01); //分类
            holder.country = (TextView) convertView.findViewById(R.id.answers_issue_live02);   //国家
            holder.details = (TextView) convertView.findViewById(R.id.answers_issue_details);
            holder.commnent_number = (TextView) convertView.findViewById(R.id.answers_issue_commnent_number); //评论数
            holder.answer = (TextView) convertView.findViewById(R.id.answers_issue_answer); //回答按钮
            holder.rbLike = (RadioButton) convertView.findViewById(R.id.rbLike);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuestionListBean mQuestionListBean = getItem(position);
        ImageLoader.getInstance().displayImage(mQuestionListBean.getHeadimg(), holder.img);
        holder.name.setText(mQuestionListBean.getAuthor());
        holder.time.setText(mQuestionListBean.getInterval());
        holder.live01.setText(mQuestionListBean.getCategory());
        holder.country.setText(mQuestionListBean.getCountry());
        holder.details.setText(mQuestionListBean.getContent());
        holder.rbLike.setText(mQuestionListBean.getView());
        String is_like = mQuestionListBean.getIslike();

        if ("1".equals(is_like)) {
            holder.rbLike.setChecked(true);
            holder.rbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLikeClickListener.onLikeClick(position);
                }
            });
        }else{
            holder.rbLike.setChecked(false);
            holder.rbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLikeClickListener.onLikeClick(position);
                }
            });
        }


        holder.commnent_number.setText(mQuestionListBean.getAnswer_num());
        //評論點擊
        holder.commnent_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentClickListener.onCommentClick(position);
            }
        });
        //回答點擊
        holder.answer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAnswerClickListener.onAnswerClick(position);

            }
        });
        return convertView;
    }
    /**
     * 点赞
     */
    /**
     * 点击回答的时候
     */
    public interface onLikeClickListener {
        void onLikeClick(int i);
    }

    private onLikeClickListener mLikeClickListener;

    public void setOnLikeClickListener(
            onLikeClickListener onLikeClickListener) {
        this.mLikeClickListener = onLikeClickListener;
    }

    /**
     * 点击回答的时候
     */
    public interface onAnswerClickListener {
        void onAnswerClick(int i);
    }

    private onAnswerClickListener mAnswerClickListener;

    public void setOnAnswerClickListener(
            onAnswerClickListener onAnswerClickListener) {
        this.mAnswerClickListener = onAnswerClickListener;
    }

    //點擊評論時候
    public interface onCommentClickListener {
        void onCommentClick(int i);
    }

    private onCommentClickListener mCommentClickListener;

    public void setOnCommentClickListener(
            onCommentClickListener onCommentClickListener) {
        this.mCommentClickListener = onCommentClickListener;
    }


    public class ViewHolder {
        private CircleImageView img;
        private RelativeLayout rlLike01;
        private RadioButton rbLike;
        private TextView name, time, details, commnent_number, live01, country, answer;
    }

}
