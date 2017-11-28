package com.likeit.as51scholarship.activitys.my_center;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hyphenate.EMCallBack;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.activitys.login.GuideActivity;
import com.likeit.as51scholarship.adapters.userapply.UserApplyDistrictAdapter;
import com.likeit.as51scholarship.adapters.userapply.UserApplyEducationAdapter;
import com.likeit.as51scholarship.chat.message.widget.DemoHelper;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.UserInfoBean;
import com.likeit.as51scholarship.model.userapply.UserDistrictBean;
import com.likeit.as51scholarship.model.userapply.UserEducationBean;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.PhotoUtils;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.ToastUtils;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.likeit.as51scholarship.view.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.utils.Logger;


public class    EditorCenterActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_birthday_arrow)
    ImageView rlBirthday;
    @BindView(R.id.address_et)
    TextView tvAddress;
    @BindView(R.id.birthday_et)
    TextView tvBirthday;
    @BindView(R.id.editorCenter_iv_logout)
    CircleImageView ivLogout;
    @BindView(R.id.select_photo)
    CircleImageView ivSelectPhoto;
    @BindView(R.id.user_avatar)
    CircleImageView ivUserAvatar;
    @BindView(R.id.username_et)
    EditText edName;
    @BindView(R.id.phone_et)
    EditText edPhone;
    @BindView(R.id.education_et)
    TextView edEducation;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    private UserInfoBean userInfoBean;
    private List<UserDistrictBean> areaData;
    //下拉
    private View layoutMenu;
    private ListView popMenuList;
    private String areaId;
    private UserInfoBean userInfobean;
    private PopupWindow popMenu;
    private PopupWindow mPopupWindow;
    private View mpopview;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;


    private String tag;

    private List<UserEducationBean> educationData;
    private List<UserDistrictBean> districtData;
    private UserApplyEducationAdapter adapter1;
    private UserApplyDistrictAdapter adapter2;
    private String education_id;
    @BindView(R.id.sex_radio_group)
    RadioGroup sexRadioGroup;
    @BindView(R.id.man_radio)
    RadioButton man_radio;
    @BindView(R.id.woman_radio)
    RadioButton woman_radio;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_center);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        educationData = new ArrayList<UserEducationBean>();
        districtData = new ArrayList<UserDistrictBean>();
        initUser();
        initData();//地区获取
        showProgress("Loading...");

      //  userInfoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");

    }
    private void initData() {
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
    private void initUser() {
        String url = AppConfig.LIKEIT_GET_INFO;
        ukey = UtilPreference.getStringValue(mContext, "ukey");
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", "用户信息-->" + response + "ukey-->" + ukey);
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    JSONObject data = object.optJSONObject("data");
                    Log.d("TAG", data.toString());
                    if ("1".equals(code)) {
                        userInfobean = JSON.parseObject(String.valueOf(data), UserInfoBean.class);
                        Log.d("TAG", "mobile3-->" + userInfobean.getNickname());
                        Log.d("TAG", "img-->" + userInfobean.getHeadimg());
                        //userInfo();
                        initView();
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
                ToastUtil.showS(mContext, "获取用户信息失败！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });
    }


    private void initView() {
        String sex1=userInfobean.getSex();
        Log.d("TAG",sex1);
        if("1".equals(sex1)){
            woman_radio.setChecked(false);
            man_radio.setChecked(true);
        }else{
            man_radio.setChecked(false);
            woman_radio.setChecked(true);
        }
        Log.d("TAG", "img22-->" + userInfobean.getHeadimg());
        tvHeader.setText("个人资料");
        tvRight.setText("保存");
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        edName.setText(userInfobean.getNickname());
        edPhone.setText(userInfobean.getMobile());
        tvBirthday.setText(userInfobean.getBirthday());
        ImageLoader.getInstance().displayImage(userInfobean.getHeadimg(),ivUserAvatar);
        tvAddress.setText(userInfobean.getCountry());
        edEducation.setText(userInfobean.getEducation());
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.man_radio:
                        sex = "1";
                        break;
                    case R.id.woman_radio:
                        sex = "2";
                        break;
                }
            }
        });
        Log.d("TAG",sex);
    }

    @OnClick({R.id.backBtn, R.id.iv_birthday_arrow, R.id.editorCenter_iv_logout,R.id.select_photo,R.id.tv_right,R.id.rl_address,R.id.rl_education,R.id.rl_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.rl_birthday:
            case R.id.iv_birthday_arrow:
                showDialog(DATE_DIALOG);
                break;
            case R.id.select_photo:
                selectAvatar();
                break;
            case R.id.rl_address:
                tag="2";
                selectMenu(tag);
                break;
            case R.id.rl_education:
                tag="1";
                selectMenu(tag);
                break;
            case R.id.tv_right:
                saveData();
                break;
            case R.id.editorCenter_iv_logout:
                logout();
                //  EMClient.getInstance().logout(true);
                Log.d("TAG", "EM成功退出");
                // MyActivityManager.getInstance().logout(mContext);
                //MyActivityManager.getInstance().appExit(mContext);
//                toActivityFinish(GuideActivity.class);
//                MyActivityManager.getInstance().finishAllActivity();
                break;
        }
    }

    private void saveData() {

        String url = AppConfig.LIKEIT_MEMBER_EDIT_INFO;
        RequestParams params = new RequestParams();
        params.put("ukey",ukey);
        params.put("country",areaId);
        params.put("education",education_id);
        params.put("birthday",tvBirthday.getText().toString());
        params.put("nickname",edName.getText().toString());
        params.put("mobile",edPhone.getText().toString());
        params.put("sex",sex);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    String message=obj.optString("message");
                    if("1".equals(code)){
                        ToastUtil.showS(mContext,"保存成功");
                        UtilPreference.saveString(mContext,"isLogin","1");
                        finish();
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
                                edEducation.setText(educationData.get(position).getName());
                            } else if ("2".equals(tag)) {
                                areaId = districtData.get(position).getId();
                                tvAddress.setText(districtData.get(position).getName());
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
    private void selectAvatar() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mpopview = inflater.inflate(R.layout.layout_choose_photo, null);
        mPopupWindow = new PopupWindow(mpopview, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.mid_filter_bg));

        //   mPopupWindow.showAsDropDown(ll_id, 0, 20, Gravity.CENTER);
        mPopupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_editor_center, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopupWindow.setOutsideTouchable(false);
        //mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setTouchable(true); // 设置popupwindow可点击
        mPopupWindow.setOutsideTouchable(true); // 设置popupwindow外部可点击
        mPopupWindow.setFocusable(true); // 获取焦点
        mPopupWindow.update();

        Button mbuttonTakePhoto = (Button) mpopview
                .findViewById(R.id.button_take_photo);
        Button mbuttonChoicePhoto = (Button) mpopview
                .findViewById(R.id.button_choice_photo);
        Button mbuttonChoicecannce = (Button) mpopview
                .findViewById(R.id.button_choice_cancer);

        // 相册上传
        mbuttonChoicePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();

                autoObtainStoragePermission();
            }
        });

        // 拍照上传
        mbuttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                autoObtainCameraPermission();
            }
        });

        mbuttonChoicecannce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 如果点击了popupwindow的外部，popupwindow也会消失
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }
    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(mContext, "com.likeit.as51scholarship.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(this, "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(mContext, "com.likeit.as51scholarship.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(this, "请允许打开相机！！");
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(this, "请允许打操作SDCard！！");
                }
                break;
        }
    }

    private static final int output_X = 480;
    private static final int output_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Log.d("TAG321",imageUri.getPath());
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.likeit.as51scholarship.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                        Log.d("TAG123",newUri.getPath());
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    Log.d("TAG555",cropImageUri.toString());
                    if (bitmap != null) {
                        showImages(bitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        String base64Token = Base64.encodeToString(bytes, Base64.DEFAULT);//  编码后
                        Log.d("TAG666",base64Token);
                         upLoad(base64Token);
                    }
                    break;
            }
        }
    }


    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    private void showImages(Bitmap bitmap) {
        ivUserAvatar.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }





    private void logout() {
        if(DemoHelper.getInstance().isLoggedIn()){
            DemoHelper.getInstance().logout(true, new EMCallBack() {

                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // show login screen
                            toActivityFinish(GuideActivity.class);
                            MyActivityManager.getInstance().finishAllActivity();

                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(mContext, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            toActivityFinish(GuideActivity.class);
            MyActivityManager.getInstance().finishAllActivity();
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(EditorCenterActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener()  {
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
        tvBirthday.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
    }

//    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            mYear = year;
//            mMonth = monthOfYear;
//            mDay = dayOfMonth;
//            display();
//        }
//    };

//    private void uploadFileBase64(String photoPath) {
//        if (photoPath == null || TextUtils.isEmpty(photoPath)) {
//            showToast("圖片不存在");
//            return;
//        }
//        Logger.d("filePath  :" + photoPath);
//        File file = new File(photoPath);
//        if (!file.exists()) {
//            showToast("圖片不存在");
//            return;
//        }
//
//        try {
//            String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
////            String  base64Token = Base64.encodeToString(bytes, Base64.DEFAULT);//  编码后
//            Log.d("TAG", "base64Token-->>" + base64Token);
//            upLoad(base64Token);
//            showProgress("Loading...");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void upLoad(String base64Token) {
        String url = AppConfig.LIKEIT_UPIMG;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("pic", base64Token);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.e("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
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
                ToastUtil.showS(mContext,"网络异常！");
            }
        });
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

}

