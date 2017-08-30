package com.likeit.a51scholarship.activitys.login;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.adapters.userapply.UserApplyDistrictAdapter;
import com.likeit.a51scholarship.adapters.userapply.UserApplyEducationAdapter;
import com.likeit.a51scholarship.adapters.userapply.UserApplyPlanTimeAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.imageutil.custom.CommandPhotoUtil01;
import com.likeit.a51scholarship.imageutil.custom.CustomScrollGridView;
import com.likeit.a51scholarship.imageutil.custom.GridAdapter01;
import com.likeit.a51scholarship.imageutil.custom.PhotoSystemOrShoot;
import com.likeit.a51scholarship.model.userapply.UserDistrictBean;
import com.likeit.a51scholarship.model.userapply.UserEducationBean;
import com.likeit.a51scholarship.model.userapply.UserPlanTimeBean;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.StringUtil;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfectFirstActivity extends Container {
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.xueli_tv)
    TextView tvXueli;
    @BindView(R.id.xueli_layout)
    LinearLayout llXueli;
    //地区
    @BindView(R.id.where_tv)
    TextView tvWhere;
    @BindView(R.id.where_layout)
    LinearLayout llWhere;
    //留学国家
    @BindView(R.id.where_country_tv)
    TextView tvWhereCountry;
    @BindView(R.id.where_country_layout)
    LinearLayout llWhereCountry;
    //学位
    @BindView(R.id.which_degree_tv)
    TextView tvWhichDegree;
    @BindView(R.id.which_degree_layout)
    LinearLayout llWhichDegree;
    //时间
    @BindView(R.id.stay_time_tv)
    TextView tvStayTime;
    @BindView(R.id.stay_time_layout)
    LinearLayout llStayTime;
    //专业
    @BindView(R.id.school_et)
    EditText etSchool;
    @BindView(R.id.profess_et)
    EditText tvProfess;
    //GPA成绩
    @BindView(R.id.gpa_et)
    EditText etGpa;
    @BindView(R.id.gpa_layout)
    LinearLayout llGpa;
    //托福
    @BindView(R.id.toefl_et)
    EditText etToefl;
    //雅思
    @BindView(R.id.yasi_et)
    EditText etYasi;
    //托业
    @BindView(R.id.toeic_et)
    EditText etToeic;
    //其他
    @BindView(R.id.other_et)
    EditText etOther;
    //中文名
    @BindView(R.id.chinese_name_et)
    EditText etChineseName;
    //英文名
    @BindView(R.id.english_name_et)
    EditText etEnglishName;
    //生日
    @BindView(R.id.date_et)
    TextView tvDate;
    @BindView(R.id.date_layout)
    LinearLayout llDate;
    //邮箱
    @BindView(R.id.email_et)
    EditText etEmail;
    //手机号
    @BindView(R.id.phone_et)
    EditText etPhone;
    @BindView(R.id.imgs_layout)
    LinearLayout imgsLayout;
    //图片添加
    @BindView(R.id.gv_all_photo)
    CustomScrollGridView mGridView;
    /**
     * GridView适配器
     */
    private GridAdapter01 gridAdapter;

    /**
     * 管理图片操作
     */
    private CommandPhotoUtil01 commandPhoto;

    /**
     * 选择图片来源
     */
    private PhotoSystemOrShoot selectPhoto;
    int mYear, mMonth, mDay;
    //下拉
    private View layoutMenu;
    private ListView popMenuList;
    private PopupWindow popMenu;
    final int DATE_DIALOG = 1;
    private List<UserEducationBean> educationData;
    private List<UserDistrictBean> districtData;
    private List<UserPlanTimeBean> planTimeData;
    private String tag;
    private UserApplyEducationAdapter adapter1;
    private UserApplyDistrictAdapter adapter2;
    private UserApplyPlanTimeAdapter adapter3;
    private String stageid;
    private String country_id;
    private String education_id;
    private String whichid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_first);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tvHeader.setText("完善信息");
        tvRight.setText("跳过");
        educationData = new ArrayList<UserEducationBean>();
        districtData = new ArrayList<UserDistrictBean>();
        planTimeData = new ArrayList<UserPlanTimeBean>();
        ininData();//註冊申请初始化数据
        showProgress("Loading..." +
                "");
        initView();
        addPlus();
    }

    private void addPlus() {
        gridAdapter = new GridAdapter01(mContext, 4);
        mGridView.setAdapter(gridAdapter);

        // 选择图片获取途径
        selectPhoto = new PhotoSystemOrShoot(mContext) {
            @Override
            public void onStartActivityForResult(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }
        };
        commandPhoto = new CommandPhotoUtil01(mContext, mGridView, gridAdapter, selectPhoto);
    }

    private void initView() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    private void ininData() {
        String url = AppConfig.LIKEIT_MEMBER_EDIT_TMPL;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONObject data = obj.optJSONObject("data");
                        JSONArray educationArray = data.optJSONArray("education");
                        for (int i = 0; i < educationArray.length(); i++) {
                            JSONObject educationObj = educationArray.optJSONObject(i);
                            UserEducationBean mUserEducationBean = new UserEducationBean();
                            mUserEducationBean.setId(educationObj.optString("id"));
                            mUserEducationBean.setName(educationObj.optString("name"));
                            educationData.add(mUserEducationBean);
                        }
                        JSONArray districtArray = data.optJSONArray("district");
                        for (int i = 0; i < districtArray.length(); i++) {
                            JSONObject districtObj = districtArray.optJSONObject(i);
                            UserDistrictBean mUserDistrictBean = new UserDistrictBean();
                            mUserDistrictBean.setId(districtObj.optString("id"));
                            mUserDistrictBean.setName(districtObj.optString("name"));
                            districtData.add(mUserDistrictBean);
                        }
                        JSONArray planTimeArray = data.optJSONArray("plan_time");
                        for (int i = 0; i < planTimeArray.length(); i++) {
                            JSONObject planTimeObj = planTimeArray.optJSONObject(i);
                            UserPlanTimeBean mUserPlanTimeBean = new UserPlanTimeBean();
                            mUserPlanTimeBean.setId(planTimeObj.optString("id"));
                            mUserPlanTimeBean.setName(planTimeObj.optString("name"));
                            planTimeData.add(mUserPlanTimeBean);
                        }
                    } else {
                        ToastUtil.showS(mContext, message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        tvDate.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    private void selectMenu(final String tag) {
        if (popMenu != null && popMenu.isShowing()) {
            popMenu.dismiss();
        } else {

            layoutMenu = this.getLayoutInflater().inflate(
                    R.layout.operationinto_popmenulist, null);
            popMenuList = (ListView) layoutMenu
                    .findViewById(R.id.menulist);

            // 创建ArrayAdapter
            if ("1".equals(tag) || "4".equals(tag)) {
                adapter1 = new UserApplyEducationAdapter(
                        mContext,
                        educationData);
                popMenuList.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            } else if ("2".equals(tag) || "3".equals(tag)) {
                adapter2 = new UserApplyDistrictAdapter(
                        mContext,
                        districtData);
                popMenuList.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();

            } else if ("5".equals(tag)) {
                adapter3 = new UserApplyPlanTimeAdapter(
                        mContext,
                        planTimeData);
                popMenuList.setAdapter(adapter3);
                adapter3.notifyDataSetChanged();

            }

            // 绑定适配器
            backgroundAlpha(0.5f);

            // 点击listview中item的处理
            popMenuList
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            // 隐藏弹出窗口
                            if (popMenu != null && popMenu.isShowing()) {
                                popMenu.dismiss();
                                backgroundAlpha(1f);
                            }
                            if ("1".equals(tag)) {
                                education_id=educationData.get(position).getId();
                                tvXueli.setText(educationData.get(position).getName());
                            } else if ("2".equals(tag)) {
                                stageid = districtData.get(position).getId();
                                tvWhere.setText(districtData.get(position).getName());
                            } else if ("3".equals(tag)) {
                                country_id=districtData.get(position).getId();
                                tvWhereCountry.setText(districtData.get(position).getName());
                            } else if ("4".equals(tag)) {
                                whichid=educationData.get(position).getId();
                                tvWhichDegree.setText(educationData.get(position).getName());
                            } else if ("5".equals(tag)) {
                                tvStayTime.setText(planTimeData.get(position).getName());
                            }
                        }
                    });

            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟tvLeft一样
            //关闭事件
            popMenu = new PopupWindow(layoutMenu, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            popMenu.showAtLocation(getLayoutInflater().inflate(R.layout.activity_school_apply, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            popMenu.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.filter_bg));
            popMenu.setAnimationStyle(R.style.AnimBottom);
            popMenu.update();
            popMenu.setOnDismissListener(new popupDismissListener());
            popMenu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popMenu.setTouchable(true); // 设置popupwindow可点击
            popMenu.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popMenu.setFocusable(true); // 获取焦点


            popMenu.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popMenu.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 获取照片返回
        if (selectPhoto != null) {
            String photoPath = selectPhoto.getPhotoResultPath(requestCode, resultCode, data);
            if (!TextUtils.isEmpty(photoPath)) {
                commandPhoto.showGridPhoto(photoPath);
            }
        }
    }

    @OnClick({R.id.backBtn, R.id.tv_right, R.id.date_layout, R.id.xueli_layout, R.id.where_layout, R.id.where_country_layout, R.id.which_degree_layout, R.id.stay_time_layout, R.id.ok_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.tv_right:
                toActivityFinish(MainActivity.class);
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                MyActivityManager.getInstance().finishAllActivity();
                break;
            case R.id.date_layout:
                showDialog(DATE_DIALOG);
                break;
            case R.id.xueli_layout:
                tag = "1";
                selectMenu(tag);
                break;
            case R.id.where_layout:
                tag = "2";
                selectMenu(tag);
                break;
            case R.id.where_country_layout:
                tag = "3";
                selectMenu(tag);
                break;
            case R.id.which_degree_layout:
                tag = "4";
                selectMenu(tag);
                break;
            case R.id.stay_time_layout:
                tag = "5";
                selectMenu(tag);
                break;
            case R.id.ok_btn:
                offerData();//提交数据
                break;
        }
    }

    private void offerData() {

        if (StringUtil.isBlank(tvXueli.getText().toString())) {
            ToastUtil.showS(mContext, "请选择学历");
        } else if (StringUtil.isBlank(tvWhere.getText().toString())) {
            ToastUtil.showS(mContext, "请选择地区");

        } else if (StringUtil.isBlank(tvWhichDegree.getText().toString())) {
            ToastUtil.showS(mContext, "请选择学位");

        } else if (StringUtil.isBlank(tvWhereCountry.getText().toString())) {
            ToastUtil.showS(mContext, "请选择留学国家");

        } else if (StringUtil.isBlank(tvStayTime.getText().toString())) {
            ToastUtil.showS(mContext, "请选择留学时间");
        } else if (StringUtil.isBlank(etGpa.getText().toString())) {
            ToastUtil.showS(mContext, "请输入GPA分数");
        } else if (StringUtil.isBlank(etToefl.getText().toString())) {
            ToastUtil.showS(mContext, "请输入托福分数");
        } else if (StringUtil.isBlank(etYasi.getText().toString())) {
            ToastUtil.showS(mContext, "请输入雅思分数");
        } else if (StringUtil.isBlank(etToeic.getText().toString())) {
            ToastUtil.showS(mContext, "请输入托业分数");
        } else if (StringUtil.isBlank(etOther.getText().toString())) {
            ToastUtil.showS(mContext, "请输入其他成绩类型、分数");
        } else if (StringUtil.isBlank(etChineseName.getText().toString())) {
            ToastUtil.showS(mContext, "请输入中文名字");
        } else if (StringUtil.isBlank(etEnglishName.getText().toString())) {
            ToastUtil.showS(mContext, "请输入英文名字");
        } else if (StringUtil.isBlank(tvDate.getText().toString())) {
            ToastUtil.showS(mContext, "请选择出生日期");
        } else if (StringUtil.isBlank(etEmail.getText().toString())) {
            ToastUtil.showS(mContext, "请输入邮箱");
        } else if (StringUtil.isBlank(etPhone.getText().toString())) {
            ToastUtil.showS(mContext, "请输入手机");
        }
        String url = AppConfig.LIKEIT_MEMBER_EDIT_INFO;
        RequestParams params = new RequestParams();
        params.put("ukey",ukey);
        params.put("country",stageid);
       // params.put("province",ukey);
        params.put("education",education_id);
        params.put("school",etSchool.getText().toString());
        params.put("professional",tvProfess.getText().toString());
        params.put("want_country",country_id);
        params.put("want_education",whichid);
        params.put("want_time",tvStayTime.getText().toString());
        params.put("score_GPA",etGpa.getText().toString());
        params.put("score_TOEFL",etToefl.getText().toString());
        params.put("score_IELTS",etYasi.getText().toString());
        params.put("score_TOEIC",etToeic.getText().toString());
//        params.put("score_SAT",ukey);
//        params.put("score_SSAT",ukey);
//        params.put("score_ACT",ukey);
//        params.put("score_GRE",ukey);
//        params.put("score_GMAT",ukey);
        params.put("score_other",etOther.getText().toString());
        params.put("nickname_cn",etChineseName.getText().toString());
        params.put("nickname_en",etEnglishName.getText().toString());
        params.put("birthday",tvDate.getText().toString());
        params.put("email",etEmail.getText().toString());
        params.put("mobile",etPhone.getText().toString());
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    String message=obj.optString("message");
                    if("1".equals(code)){
                        ToastUtil.showS(mContext,message);
                        toActivityFinish(MainActivity.class);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        MyActivityManager.getInstance().finishAllActivity();
                    }else{
                        ToastUtil.showS(mContext,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }
}
