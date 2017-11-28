package com.likeit.as51scholarship.activitys.login;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.activitys.MainActivity;
import com.likeit.as51scholarship.activitys.SchoolApplyActivity;
import com.likeit.as51scholarship.activitys.my_center.EditorCenterActivity;
import com.likeit.as51scholarship.adapters.userapply.UserApplyDistrictAdapter;
import com.likeit.as51scholarship.adapters.userapply.UserApplyEducationAdapter;
import com.likeit.as51scholarship.adapters.userapply.UserApplyPlanTimeAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.imageutil.custom.CropUtils;
import com.likeit.as51scholarship.imageutil.custom.CustomScrollGridView;
import com.likeit.as51scholarship.imageutil.custom.DialogPermission;
import com.likeit.as51scholarship.imageutil.custom.FileUtil;
import com.likeit.as51scholarship.imageutil.custom.GridViewAddImgesAdpter;
import com.likeit.as51scholarship.imageutil.custom.PermissionUtil;
import com.likeit.as51scholarship.imageutil.custom.SharedPreferenceMark;
import com.likeit.as51scholarship.model.userapply.UserDistrictBean;
import com.likeit.as51scholarship.model.userapply.UserEducationBean;
import com.likeit.as51scholarship.model.userapply.UserPlanTimeBean;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import net.bither.util.NativeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //图片添加
    @BindView(R.id.gv_all_photo)
    CustomScrollGridView mGridView;

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
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;
    private Uri photoUri;
    private File file;
    private Uri uri;

    /* 头像名称 */
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private List<Map<String, Object>> datas;
    private Dialog dialog;




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
        datas = new ArrayList<>();
        init();
        ininData();//註冊申请初始化数据
        showProgress("Loading..." +
                "");
        initView();
    }
    private void init() {
        file = new File(FileUtil.getCachePath(this), "user-avatar.jpg");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要这样的方法跨应用访问)
            uri = FileProvider.getUriForFile(mContext, "com.likeit.as51scholarship.fileprovider", file);
        }
    }

    private void initView() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        mGridView.setAdapter(gridViewAddImgesAdpter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showdialog();
            }
        });
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
                return new DatePickerDialog(PerfectFirstActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener()  {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        display();
                    }
                }, 2017, 01, 01);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        //tvDate.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
        tvDate.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
    }


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
            return;
        } else if (StringUtil.isBlank(tvWhere.getText().toString())) {
            ToastUtil.showS(mContext, "请选择地区");
            return;
        } else if (StringUtil.isBlank(tvWhichDegree.getText().toString())) {
            ToastUtil.showS(mContext, "请选择学位");

        } else if (StringUtil.isBlank(tvWhereCountry.getText().toString())) {
            ToastUtil.showS(mContext, "请选择留学国家");
            return;
        } else if (StringUtil.isBlank(tvStayTime.getText().toString())) {
            ToastUtil.showS(mContext, "请选择留学时间");
            return;
        } else if (StringUtil.isBlank(etChineseName.getText().toString())) {
            ToastUtil.showS(mContext, "请输入中文名字");
            return;
        } else if (StringUtil.isBlank(etEnglishName.getText().toString())) {
            ToastUtil.showS(mContext, "请输入英文名字");
            return;
        } else if (StringUtil.isBlank(tvDate.getText().toString())) {
            ToastUtil.showS(mContext, "请选择出生日期");
            return;
        } else if (StringUtil.isBlank(etEmail.getText().toString())) {
            ToastUtil.showS(mContext, "请输入邮箱");
            return;
        } else if (StringUtil.isBlank(etPhone.getText().toString())) {
            ToastUtil.showS(mContext, "请输入手机");
            return;
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
    /**
     * 选择图片对话框
     */
    public void showdialog() {
        View localView = LayoutInflater.from(this).inflate(
                R.layout.popup_select_way_photo, null);
        TextView tv_camera = (TextView) localView.findViewById(R.id.tv_bitmap_shoot);
        TextView tv_gallery = (TextView) localView.findViewById(R.id.tv_bitmap_system);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_bitmap_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 拍照
                if (PermissionUtil.hasCameraPermission(PerfectFirstActivity.this)) {
                    uploadAvatarFromPhotoRequest();
                }
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
                uploadAvatarFromAlbumRequest();
            }
        });
    }
    /**
     * photo
     */
    private void uploadAvatarFromPhotoRequest() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    /**
     * album
     */
    private void uploadAvatarFromAlbumRequest() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == REQUEST_CODE_ALBUM && data != null) {
            Uri newUri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                newUri = Uri.parse("file:///" + CropUtils.getPath(this, data.getData()));
            } else {
                newUri = data.getData();
            }
            if (newUri != null) {
                startPhotoZoom(newUri);
            } else {
                Toast.makeText(this, "没有得到相册图片", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            startPhotoZoom(uri);
        } else if (requestCode == REQUEST_CODE_CROUP_PHOTO) {
            uploadAvatarFromPhoto();
        }
    }

    private void uploadAvatarFromPhoto() {
        // compressAndUploadAvatar(file.getPath());
        uploadImage(file.getPath());
    }



    /**
     * 裁剪拍照裁剪
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
//        intent.putExtra("outputX", 400);//图片输出大小
//        intent.putExtra("outputY", 400);
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PermissionUtil.REQUEST_SHOWCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    uploadAvatarFromPhotoRequest();

                } else {
                    if (!SharedPreferenceMark.getHasShowCamera()) {
                        SharedPreferenceMark.setHasShowCamera(true);
                        new DialogPermission(this, "关闭摄像头权限影响扫描功能");

                    } else {
                        Toast.makeText(this, "未获取摄像头权限", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }

        }
    };

    /**
     * 上传图片
     *
     * @param path
     */
    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                if (new File(path).exists()) {
                    Log.d("images", "源文件存在" + path);
                } else {
                    Log.d("images", "源文件不存在" + path);
                }

                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir + "/temp_photo" + System.currentTimeMillis() + ".jpg");
                NativeUtil.compressBitmap(path, file.getAbsolutePath(), 50);
                if (file.exists()) {
                    Log.d("images", "压缩后的文件存在" + file.getAbsolutePath());
                } else {
                    Log.d("images", "压缩后的不存在" + file.getAbsolutePath());
                }
                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);

            }
        }.start();

    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }

}
