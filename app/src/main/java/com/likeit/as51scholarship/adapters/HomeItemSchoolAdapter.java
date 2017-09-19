package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.HomeItemSchoolBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Administrator on 2017\7\22 0022.
 */

public class HomeItemSchoolAdapter extends MyBaseAdapter<HomeItemSchoolBean> {
    private DisplayImageOptions options;

    public HomeItemSchoolAdapter(Context context, List<HomeItemSchoolBean> homeItemSchoolBeen) {
        super(context, homeItemSchoolBeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.item_main_school_list_item, parent, false);
            holder.school_back_img = (ImageView) convertView
                    .findViewById(R.id.school_back_img);
            holder.address_tv = (TextView) convertView
                    .findViewById(R.id.address_tv);
            holder.school_name_tv = (TextView) convertView
                    .findViewById(R.id.school_name_tv);
            holder.school_es_name_tv = (TextView) convertView
                    .findViewById(R.id.school_es_name_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeItemSchoolBean data1 = getItem(position);
        holder.address_tv.setText(data1.getCountry_name());
        holder.school_name_tv.setText(data1.getName());
        holder.school_es_name_tv.setText(data1.getEn_name());
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
        ImageLoader.getInstance().displayImage(data1.getImg(), holder.school_back_img, options);
        return convertView;
    }

    private class ViewHolder {
        TextView address_tv, school_name_tv, school_es_name_tv;
        ImageView school_back_img;

    }
}
