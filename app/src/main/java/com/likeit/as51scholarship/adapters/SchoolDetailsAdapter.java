package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.model.SchoolDetailsBean01;
import com.likeit.as51scholarship.utils.richtext.RichText;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class SchoolDetailsAdapter extends MyBaseAdapter<SchoolDetailsBean01> {
    private int selectItem = -1;
    public SchoolDetailsAdapter(Context context, List<SchoolDetailsBean01> schoolDetailsBean01s) {
        super(context, schoolDetailsBean01s);
    }
    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.shool_details_listview_items, parent, false);
            holder.schoolDetailsName = (TextView) convertView
                    .findViewById(R.id.school_details_name);
            holder.schoolDetails = (RichText) convertView
                    .findViewById(R.id.school_details_details);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SchoolDetailsBean01 data1 = getItem(position);
        holder.schoolDetailsName.setText(data1.getName());
        holder.schoolDetails.setRichText(data1.getContent());
        return convertView;
    }

    private class ViewHolder {
        TextView schoolDetailsName;
        RichText schoolDetails;

    }


}