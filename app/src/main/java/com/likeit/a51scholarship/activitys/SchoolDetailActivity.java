package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.utils.MyActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SchoolDetailActivity extends Container {
    @BindView(R.id.school_logo)
    ImageView schoolLogo;
    @BindView(R.id.school_name_tv)
    TextView schoolNameTv;
    @BindView(R.id.school_es_name_tv)
    TextView schoolEsNameTv;
    @BindView(R.id.city_tv)
    TextView cityTv;
    @BindView(R.id.school_icon)
    ImageView schoolIcon;
    @BindView(R.id.apply_btn)
    TextView applyBtn;
    @BindView(R.id.school_sex_tv)
    TextView schoolSexTv;
    @BindView(R.id.school_sort_tv)
    TextView schoolSortTv;
    @BindView(R.id.school_money_tv)
    TextView schoolMoneyTv;
    @BindView(R.id.school_record_tv)
    TextView schoolRecordTv;
    @BindView(R.id.sat_num_tv)
    TextView satNumTv;
    @BindView(R.id.toefl_num_tv)
    TextView toeflNumTv;
    @BindView(R.id.act_num_tv)
    TextView actNumTv;
    @BindView(R.id.perpal_num_tv)
    TextView perpalNumTv;
    @BindView(R.id.gpa_num_tv)
    TextView gpaNumTv;
    @BindView(R.id.school_desc_tv)
    TextView schoolDescTv;
    @BindView(R.id.collect_layout)
    LinearLayout collectLayout;
    @BindView(R.id.share_layout)
    LinearLayout shareLayout;
    @BindView(R.id.comment_layout)
    LinearLayout commentLayout;
    @BindView(R.id.qunzhu_layout)
    LinearLayout qunzhuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initTitle("学校详情");
    }

    @OnClick({R.id.school_logo, R.id.school_icon, R.id.apply_btn, R.id.collect_layout, R.id.share_layout, R.id.comment_layout, R.id.qunzhu_layout, R.id.backBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.school_logo:
                break;
            case R.id.school_icon:
                break;
            case R.id.apply_btn:
                break;
            case R.id.collect_layout:
                break;
            case R.id.share_layout:
                break;
            case R.id.comment_layout:
                break;
            case R.id.qunzhu_layout:
                break;
        }
    }
}
