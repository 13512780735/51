package com.likeit.a51scholarship.activitys.userdetailsfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.fragments.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment01 extends BaseFragment {


    @Override
    protected int setContentView() {
        return R.layout.fragment_user_details_fragment01;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    private void initView() {
        TextView tvDetails = findViewById(R.id.tvDetails);
        tvDetails.setText("\t\t"+"留学生一词起源于中国唐朝时期中日文化交流，意为当遣唐使回国后任然留在中国学习的日本" +
                "学生，现在泛指留局外国学习或研究的学生。" +
                "\n"+"\t\t"+"教育部数据显示，2015年度中国出国留学人员总数为52.37万人，回国40.91万人，较上年增长了12.1%。而在从1978年到2015年底的37年间，走出国门" +
                "的留学生累计达404.21万人，毕业后回国发展的占79.87%。");
    }

    public static Fragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        UserDetailsFragment01 fragment = new UserDetailsFragment01();
        fragment.setArguments(args);
        return fragment;
    }
}
