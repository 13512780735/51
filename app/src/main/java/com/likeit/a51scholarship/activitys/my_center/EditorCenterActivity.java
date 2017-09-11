package com.likeit.a51scholarship.activitys.my_center;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.activitys.login.GuideActivity;
import com.likeit.a51scholarship.activitys.login.PerfectFirstActivity;
import com.likeit.a51scholarship.adapters.userapply.UserApplyDistrictAdapter;
import com.likeit.a51scholarship.adapters.userapply.UserApplyEducationAdapter;
import com.likeit.a51scholarship.adapters.userapply.UserApplyPlanTimeAdapter;
import com.likeit.a51scholarship.chat.message.widget.DemoHelper;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.imageutil.custom.PhotoSystemOrShoot;
import com.likeit.a51scholarship.model.UserInfoBean;
import com.likeit.a51scholarship.model.userapply.UserDistrictBean;
import com.likeit.a51scholarship.utils.BitmapOption;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.utils.UtilPreference;
import com.likeit.a51scholarship.view.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pk4pk.baseappmoudle.utils.FileUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.utils.Logger;


public class EditorCenterActivity extends Container {
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
    EditText edEducation;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    private UserInfoBean userInfoBean;
    private PhotoSystemOrShoot selectPhoto;
    private List<UserDistrictBean> areaData;
    //下拉
    private View layoutMenu;
    private ListView popMenuList;
    private PopupWindow popMenu;
    private UserApplyDistrictAdapter adapter1;
    private String areaId;
    private UserInfoBean userInfobean;
    private PopupWindow mPopupWindow;
    private View mpopview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_center);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        areaData=new ArrayList<UserDistrictBean>();
        initUser();
        initData();//地区获取
        showProgress("Loading...");
      //  userInfoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");

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

    private void initData() {
        String url=AppConfig.LIKEIT_MEMBER_EDIT_DISTRICT;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    if("1".equals(code)){
                        JSONArray array=obj.optJSONArray("data");
                        for(int i=0;i<array.length();i++){
                            JSONObject object=array.optJSONObject(i);
                            UserDistrictBean mUserDistrictBean=new UserDistrictBean();
                            mUserDistrictBean.setId(object.optString("id"));
                            mUserDistrictBean.setName(object.optString("name"));
                            areaData.add(mUserDistrictBean);
                        }
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

    private void initView() {
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
        tvAddress.setText(userInfobean.getPos_province()+userInfobean.getCountry());
        edEducation.setText(userInfobean.getEducation());
    }

    @OnClick({R.id.backBtn, R.id.iv_birthday_arrow, R.id.editorCenter_iv_logout,R.id.select_photo,R.id.tv_right,R.id.rl_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.iv_birthday_arrow:
                showDialog(DATE_DIALOG);
                break;
            case R.id.select_photo:
                selectAvatar();
                break;
            case R.id.rl_address:
                selectMenu();
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
       // params.put("education",education_id);
        params.put("birthday",tvBirthday.getText().toString());
        params.put("nickname_cn",edName.getText().toString());
        params.put("mobile",edPhone.getText().toString());
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

    private void selectMenu() {
        if (popMenu != null && popMenu.isShowing()) {
            popMenu.dismiss();
        } else {

            layoutMenu = this.getLayoutInflater().inflate(
                    R.layout.operationinto_popmenulist, null);
            popMenuList = (ListView) layoutMenu
                    .findViewById(R.id.menulist);

            // 创建ArrayAdapter
                adapter1 = new UserApplyDistrictAdapter(
                        mContext,
                        areaData);
                popMenuList.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();

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
                                areaId=areaData.get(position).getId();
                                tvAddress.setText(areaData.get(position).getName());

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
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                startActivityForResult(i, 2);
            }
        });

        // 拍照上传
        mbuttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
                @SuppressWarnings("unused")
                Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                startActivityForResult(intent, 1);
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
    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) { // 拍照
                Bundle extras = data.getExtras();
                Bitmap photoBit = (Bitmap) extras.get("data");
                Bitmap option = BitmapOption.bitmapOption(photoBit, 5);
                ivUserAvatar.setImageBitmap(option);
                Log.d("TAG",data.toString());
                saveBitmap2file(option, "0001.jpg");
                final File file = new File("/sdcard/" + "0001.jpg");
                try {
                    String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
                    upLoad(base64Token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("TAG", "file333333333333333   " + file.getName());
                // 开始联网上传的操作

            } else if (requestCode == 2) { // 相册
                try {
                    Uri uri = data.getData();
                    String[] pojo = { MediaStore.Images.Media.DATA };
                    @SuppressWarnings("deprecation")
                    Cursor cursor = managedQuery(uri, pojo, null, null, null);
                    if (cursor != null) {
                        ContentResolver cr = this.getContentResolver();
                        int colunm_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String path = cursor.getString(colunm_index);
                        final File file = new File(path);
                        Bitmap bitmap = BitmapFactory.decodeStream(cr
                                .openInputStream(uri));
                        Bitmap option = BitmapOption.bitmapOption(bitmap, 5);
                        ivUserAvatar.setImageBitmap(option);
                        //  iv01.setImageBitmap(option);// 设置为头像的背景
                        Log.e("TAG", "fiels11111  " + file.getName());
                        try {
                            String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
                            upLoad(base64Token);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 开始联网上传的操作

                    }
                } catch (Exception e) {

                }
            }
        }

    }

    private void logout() {
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
        tvBirthday.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
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

    private void uploadFileBase64(String photoPath) {
        if (photoPath == null || TextUtils.isEmpty(photoPath)) {
            showToast("圖片不存在");
            return;
        }
        Logger.d("filePath  :" + photoPath);
        File file = new File(photoPath);
        if (!file.exists()) {
            showToast("圖片不存在");
            return;
        }

        try {
            String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
//            String  base64Token = Base64.encodeToString(bytes, Base64.DEFAULT);//  编码后
            Log.d("TAG", "base64Token-->>" + base64Token);
            upLoad(base64Token);
            showProgress("Loading...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    @SuppressLint("SdCardPath")
    static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" + filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }
}

