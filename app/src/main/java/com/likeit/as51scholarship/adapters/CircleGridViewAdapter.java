package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.circle_model.FollowCircleModel;
import com.likeit.as51scholarship.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;



/**
 * Created by Administrator on 2017/7/26.
 */

public class CircleGridViewAdapter extends MyBaseAdapter<FollowCircleModel> {

    //List<FollowCircleModel> listFollowCircle;
    public CircleGridViewAdapter(Context context, List<FollowCircleModel> listFollowCircle) {
        super(context, listFollowCircle);
    }


//    @Override
//    public int getCount() {
//        return 8;//注意此处
//    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.cricle_gridview_items, parent, false);
            holder.ivAvatar = (ImageView) convertView
                    .findViewById(R.id.cricle_gridview_iv_Avatar);
            holder.tvTitle = (Button) convertView
                    .findViewById(R.id.cricle_gridview_tv_name);
            holder.tvTitle01 = (Button) convertView
                    .findViewById(R.id.cricle_gridview_tv_name01);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FollowCircleModel data= getItem(position);
      //  ImageLoader.getInstance().displayImage(data1.getImg(), holder.school_listview_iv_school);
        String imgUrl=data.getLogo();
        if(StringUtil.isBlank(imgUrl)){
            holder.ivAvatar.setImageResource(R.mipmap.icon_01_3x);
        }else{
        ImageLoader.getInstance().displayImage(imgUrl, holder.ivAvatar);
         //   holder.ivAvatar.setImageResource(R.mipmap.icon_01_3x);
        }
        holder.tvTitle.setText(data.getTitle());
//        if(listFollowCircle.size()==7){
//            holder.tvTitle.setVisibility(View.VISIBLE);
//        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivAvatar;
        private Button tvTitle;
        private Button tvTitle01;

    }

}
