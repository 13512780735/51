package com.likeit.as51scholarship.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.likeit.as51scholarship.imageutil.custom.CropUtils;
import com.likeit.as51scholarship.imageutil.custom.CustomScrollGridView;
import com.likeit.as51scholarship.imageutil.custom.DialogPermission;
import com.likeit.as51scholarship.imageutil.custom.FileUtil;
import com.likeit.as51scholarship.imageutil.custom.GridViewAddImgesAdpter;
import com.likeit.as51scholarship.imageutil.custom.PermissionUtil;
import com.likeit.as51scholarship.imageutil.custom.SharedPreferenceMark;
import com.likeit.as51scholarship.model.NewTabBean;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.GridViewForScrollView;
import com.loopj.android.http.RequestParams;
import net.bither.util.NativeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @BindView(R.id.addnews_gridview)
    GridViewForScrollView mGridView01;
    //图片添加
    @BindView(R.id.gv_all_photo)
    CustomScrollGridView mGridView;
    private NewTabBean mNewTabBean;
    private List<NewTabBean> mDatas;
    private NewategoryAdapter adapter;
    private String cid;
    private int selectPosition = -1;//用于记录用户选择的变量
    private String uId;
    private String gid;

   // 上传图片
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
    private String cover_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_news);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        uId = intent.getStringExtra("uId");
        datas = new ArrayList<>();
        init();
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
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        mGridView.setAdapter(gridViewAddImgesAdpter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showdialog();
            }
        });
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




    @OnClick({R.id.backBtn, R.id.tv_right, R.id.photo_im, R.id.camear_im, R.id.label_im})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.tv_right:
                if("1".equals(uId)){
                    send();

                }else{
                    send01();

                }
                break;
            case R.id.label_im:
                initImg();
                labelIm.setImageResource(R.mipmap.btn_icon_label_selected);
                break;
        }
    }

    private void send01() {
        //帖子
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(gid)) {
            showToast("标题或内容不能为空!");
            return;
        }
        if(TextUtils.isEmpty(cover_id)){
            showToast("请选择图片");
            return;
        }
        String url = AppConfig.LIKEIT_GROUP_ADDPOST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("title", title);
        params.put("content", content);
        params.put("gid", gid);
        params.put("cover_id", cover_id);

        showProgress("Loading...");
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
        // 资讯
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            showToast("标题或内容不能为空!");
            return;
        }
        if(TextUtils.isEmpty(cid)){
            showToast("请选择资讯分类");
            return;
        }
        if(TextUtils.isEmpty(cover_id)){
            showToast("请选择图片");
            return;
        }
        String url = AppConfig.LIKEIT_NEW_ADDNEWS;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("title", title);
        params.put("content", content);
        params.put("cover_id", cover_id);
        params.put("cid", cid);
        showProgress("Loading...");
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
            WindowManager wm = (WindowManager) getApplication()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            viewHolder.select.setWidth(Integer.valueOf(width/4)-20);
            viewHolder.select.setGravity(Gravity.CENTER);
            if (selectPosition == position) {
                viewHolder.select.setChecked(true);
                viewHolder.select.setBackgroundResource(R.drawable.goods_attr_selected_shape);
                viewHolder.select.setTextColor(Color.WHITE);
            } else {
                viewHolder.select.setChecked(false);
                viewHolder.select.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
                viewHolder.select.setTextColor(Color.DKGRAY);
            }
            return convertView;
        }

        public class ViewHolder {
            RadioButton select;
        }
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
                if (PermissionUtil.hasCameraPermission(SendNewsActivity.this)) {
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
        String base64Token = fileToBase64(file.getAbsoluteFile());//  编码后
        uploadImage1(base64Token);
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

    private void uploadImage1(String base64Token) {
        String url = AppConfig.LIKEIT_UPLOAD_FORBASE64_01;
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
                        JSONObject object=obj.optJSONObject("data");
                        cover_id=object.optString("id");
                        String path=object.getString("path");
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

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }

    /**
     * 文件转base64字符串
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return base64;
    }

}
