package com.likeit.a51scholarship.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.likeit.a51scholarship.model.Toolsbean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class ToolsAdater extends MyBaseAdapter<Toolsbean> {
    public ToolsAdater(Context context, List<Toolsbean> toolsbeen) {
        super(context, toolsbeen);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
