package com.likeit.as51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.likeit.as51scholarship.model.SchoolDetailsPopBena;

import java.util.List;

/**
 * Created by Administrator on 2017\8\28 0028.
 */

public class SchoolDetailPopAdapter extends MyBaseAdapter<SchoolDetailsPopBena> {
    private int defItem;//声明默认选中的项
    public SchoolDetailPopAdapter(Context context, List<SchoolDetailsPopBena> groupListFilterModels) {
        super(context, groupListFilterModels);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
//            convertView = getInflater().inflate(
//                    R.layout.school_details_popwindow_listview_itmes, parent, false);
//            holder.btnName = (CheckBox) convertView
//                    .findViewById(R.id.pop_btn);
//            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        SchoolDetailsPopBena mSchoolDetailsPopBena = getItem(position);
//        holder.btnName.setText(mSchoolDetailsPopBena.getName());
        return convertView;
    }
    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }
    public class ViewHolder {
        CheckBox btnName;
    }
}
