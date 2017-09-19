package com.likeit.as51scholarship.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.imageutil.custom.CommandPhotoUtil;
import com.likeit.as51scholarship.imageutil.custom.CustomScrollGridView;
import com.likeit.as51scholarship.imageutil.custom.GridAdapter;
import com.likeit.as51scholarship.imageutil.custom.PhotoSystemOrShoot;
import com.likeit.as51scholarship.model.NewTabBean;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.GridViewForScrollView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class SendNewsActivity extends Container {

    @BindView(R.id.tv_header)
    TextView topBarTitle;
    @BindView(R.id.tv_right)
    TextView topBarRightTv;
    @BindView(R.id.tv_selcet)
    TextView tvSelcet;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.label_im)
    ImageView labelIm;
    @BindView(R.id.imgs_layout)
    LinearLayout imgsLayout;
    @BindView(R.id.addnews_gridview)
    GridViewForScrollView mGridView01;
    //图片添加
    @BindView(R.id.gv_all_photo)
    CustomScrollGridView mGridView;
    /**
     * GridView适配器
     */
    private GridAdapter gridAdapter;

    /**
     * 管理图片操作
     */
    private CommandPhotoUtil commandPhoto;

    /**
     * 选择图片来源
     */
    private PhotoSystemOrShoot selectPhoto;
    private NewTabBean mNewTabBean;
    private List<NewTabBean> mDatas;
    private NewategoryAdapter adapter;
    private String cid;
    private int selectPosition = -1;//用于记录用户选择的变量
    private String uId;
    private String gid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_news);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        uId = intent.getStringExtra("uId");
        if ("1".equals(uId)) {
            initTitle("发布资讯");
            initData();//资讯Id获取
            showProgress("Loading...");
        } else {
            initTitle("发布帖子");
            gid=intent.getStringExtra("gid");
            llSelect.setVisibility(View.INVISIBLE);
            tvSelcet.setVisibility(View.INVISIBLE);
            mGridView01.setVisibility(View.INVISIBLE);
        }

        topBarRightTv.setText("发布");
        mDatas = new ArrayList<NewTabBean>();
        addPlus();
    }

    private void initData() {
        String page = "1";
        String url = AppConfig.LIKEIT_NEW_GETCATEGORY;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", page);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        //  NewTabBean mNewTabBean= JSON.parseArray(obj.optJSONArray("data"),NewTabBean.class);
                        // mNewTabBean= JSON.parseObject(obj.optJSONArray("data").toString(),NewTabBean.class);
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            mNewTabBean = new NewTabBean();
                            mNewTabBean.setId(object.optString("id"));
                            mNewTabBean.setTitle(object.optString("title"));
                            mDatas.add(mNewTabBean);
                        }
                        initView();
                    }
                    Log.d("TAG666", mNewTabBean.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {

                disShowProgress();
                showErrorMsg("网络异常");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });
    }

    private void initView() {
        adapter = new NewategoryAdapter(mContext, mDatas);
        mGridView01.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mGridView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TAG", position + "");
                cid = mDatas.get(position).getId();
                selectPosition = position;
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 实例化组件
     */
    private void addPlus() {
        gridAdapter = new GridAdapter(mContext, 4);
        mGridView.setAdapter(gridAdapter);

        // 选择图片获取途径
        selectPhoto = new PhotoSystemOrShoot(mContext) {
            @Override
            public void onStartActivityForResult(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }
        };
        commandPhoto = new CommandPhotoUtil(mContext, mGridView, gridAdapter, selectPhoto);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContext, "请授予打开相机的权限", Toast.LENGTH_SHORT).show();
            List<PermissionItem> permissions = new ArrayList<PermissionItem>();
            permissions.add(new PermissionItem(Manifest.permission.CAMERA, "Camera", R.drawable.permission_ic_camera));
            HiPermission.create(mContext)
                    .permissions(permissions)
                    .msg("是否授予打开相机的权限")
                    .animStyle(R.style.PermissionAnimModal)
//                        .style(R.style.CusStyle)
                    .checkMutiPermission(new PermissionCallback() {
                        @Override
                        public void onClose() {
                            Log.i(TAG, "onClose");
                            ToastUtil.showS(mContext, "权限被拒绝");
                        }

                        @Override
                        public void onFinish() {
                            ToastUtil.showS(mContext, "权限已被开启");
                        }

                        @Override
                        public void onDeny(String permission, int position) {
                            Log.i(TAG, "onDeny");
                        }

                        @Override
                        public void onGuarantee(String permission, int position) {
                            Log.i(TAG, "onGuarantee");
                        }
                    });
            return;
        }
    }


    @OnClick({R.id.backBtn, R.id.tv_right, R.id.photo_im, R.id.camear_im, R.id.label_im})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.tv_right:
                if("1".equals(uId)){
                    send();
                    showProgress("Loading...");
                }else{
                    send01();
                    showProgress("Loading...");
                }

                break;
//            case R.id.photo_im:
//                initImg();
//                photoIm.setImageResource(R.mipmap.btn_icon_photo_selected);
//                break;
//            case R.id.camear_im:
//                initImg();
//                camearIm.setImageResource(R.mipmap.btn_icon_photographs_selected);
//                break;
            case R.id.label_im:
                initImg();
                labelIm.setImageResource(R.mipmap.btn_icon_label_selected);
                break;
        }
    }

    private void send01() {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(gid)) {
            showToast("标题或内容不能为空!");
            return;
        }
        String url = AppConfig.LIKEIT_GROUP_ADDPOST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("title", title);
        params.put("content", content);
        params.put("gid", gid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        finish();
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
                showErrorMsg("网络异常");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });
    }

    private void initImg() {
//        photoIm.setImageResource(R.mipmap.btn_icon_photo_default);
//        camearIm.setImageResource(R.mipmap.btn_icon_photographs_default);
        labelIm.setImageResource(R.mipmap.btn_icon_label_default);

    }

    private void send() {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(cid)) {
            showToast("标题或内容不能为空!");
            return;
        }
        String url = AppConfig.LIKEIT_NEW_ADDNEWS;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("title", title);
        params.put("content", content);
        params.put("cid", cid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        finish();
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
                showErrorMsg("网络异常");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });

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

    public class NewategoryAdapter extends BaseAdapter {

        Context context;
        List<NewTabBean> brandsList;
        LayoutInflater mInflater;

        public NewategoryAdapter(Context context, List<NewTabBean> mList) {
            this.context = context;
            this.brandsList = mList;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return brandsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.school_filter_gridview_items01, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.select = (RadioButton) convertView.findViewById(R.id.id_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.select.setText(brandsList.get(position).getTitle());

            if (selectPosition == position) {
                viewHolder.select.setChecked(true);
            } else {
                viewHolder.select.setChecked(false);
            }
            return convertView;
        }

        public class ViewHolder {
            RadioButton select;
        }
    }
}
