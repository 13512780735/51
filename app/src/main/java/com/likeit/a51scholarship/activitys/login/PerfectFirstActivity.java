package com.likeit.a51scholarship.activitys.login;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.likeit.a51scholarship.R;
import com.pk4pk.baseappmoudle.utils.DateUtil;
import com.pk4pk.baseappmoudle.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.utils.MyActivityManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.utils.Logger;

public class PerfectFirstActivity extends Container {
    @BindView(R.id.xueli_tv)
    TextView xueliTv;
    @BindView(R.id.xueli_layout)
    LinearLayout xueliLayout;
    @BindView(R.id.where_tv)
    TextView whereTv;
    @BindView(R.id.where_layout)
    LinearLayout whereLayout;
    @BindView(R.id.school_et)
    EditText schoolEt;
    @BindView(R.id.profess_et)
    EditText professEt;
    @BindView(R.id.language_tv)
    TextView languageTv;
    @BindView(R.id.language_layout)
    LinearLayout languageLayout;
    @BindView(R.id.language_num_et)
    EditText languageNumEt;
    @BindView(R.id.ok_btn)
    TextView okBtn;
    @BindView(R.id.where_country_tv)
    TextView whereCountryTv;
    @BindView(R.id.where_country_layout)
    LinearLayout whereCountryLayout;
    @BindView(R.id.which_degree_tv)
    TextView whichDegreeTv;
    @BindView(R.id.which_degree_layout)
    LinearLayout whichDegreeLayout;
    @BindView(R.id.stay_time_tv)
    TextView stayTimeTv;
    @BindView(R.id.stay_time_layout)
    LinearLayout stayTimeLayout;
    @BindView(R.id.gpa_conver_tv)
    TextView gpaConverTv;
    @BindView(R.id.gpa_et)
    EditText gpaEt;
    @BindView(R.id.gpa_layout)
    LinearLayout gpaLayout;
    @BindView(R.id.chinese_name_et)
    EditText chineseNameEt;
    @BindView(R.id.english_name_et)
    EditText englishNameEt;
    @BindView(R.id.date_et)
    TextView dateEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.update_img_layout)
    LinearLayout updateImgLayout;
    @BindView(R.id.update_img_btn)
    ImageView updateImgBtn;
    @BindView(R.id.backBtn)
    Button btback;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    // private EduTypeInfoEntity eduTypeInfoEntity;
    private int isEduType = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_first);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tvHeader.setText("完善信息");
        tvRight.setText("跳过");
    }

    @OnClick({R.id.xueli_layout, R.id.where_layout, R.id.language_layout, R.id.ok_btn,
            R.id.where_country_layout, R.id.which_degree_layout, R.id.stay_time_layout, R.id.gpa_conver_tv,
            R.id.update_img_btn, R.id.date_layout, R.id.backBtn,R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.xueli_layout:
                // xueliInfo(0);
                break;
            case R.id.where_layout:
                //  whereInfo("0");
                break;
            case R.id.language_layout:
                //   xueliInfo(1);
                break;
            case R.id.where_country_layout:
                //  whereCountryLayout();
                break;
            case R.id.which_degree_layout:
                //   xueliInfo(2);
                break;
            case R.id.stay_time_layout:
                // stayTime();
                break;
            case R.id.date_layout:
                showDialog(DATE_DIALOG);
                break;
            case R.id.gpa_conver_tv:
                // toWebActivity("","GPA换算");
                break;
            case R.id.update_img_btn:
                openImgSelect();
                break;
            case R.id.tv_right:
            case R.id.ok_btn:
                //完善信息按钮
                //  okBtn();
                toActivityFinish(MainActivity.class);
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                MyActivityManager.getInstance().finishAllActivity();
                break;
        }
    }

    private int DATE_DIALOG = 101;

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG) {
            mYear = DateUtil.getYear();
            mMonth = DateUtil.getMonth();
            mDay = DateUtil.getDay();
            Logger.d("mYear :" + mYear + " mMonth :" + mMonth + "  mDay:" + mDay);
            return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return super.onCreateDialog(id);
    }

    private void openImgSelect() {
        RxGalleryFinal
                .with(mContext)
                .image()
                .radio()
                .crop()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        //图片选择结果
                        String cropPath = imageRadioResultEvent.getResult().getCropPath();
                        Logger.d("cropPath :" + cropPath);
//                        uploadFileBase64(cropPath);
                        showImg(cropPath);
                    }
                })
                .openGallery();
    }

    private List<String> imgPaths = new ArrayList<>();
    private Map<Integer, String> imgUrl = new HashMap<>();

    private void showImg(String filePath) {
        if (!FileUtil.isExists(filePath)) {
            showToast("图片不存在");
            return;
        }
        imgPaths.add(filePath);
        ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_perfect_image_view, updateImgLayout, false);
        File file = new File(filePath);
        //加载图片
        Glide.with(this).load(file).into(imageView);
        updateImgLayout.addView(imageView);
    }

    private void uploadFileBase64(final int index, String filePath) {
        if (!FileUtil.isExists(filePath)) {
            showToast("图片不存在");
            return;
        }
        File file = new File(filePath);
        try {
            String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
//            String  base64Token = Base64.encodeToString(bytes, Base64.DEFAULT);//  编码后
//            Logger.d("base64Token  start");
//            Logger.d("base64Token  :" + base64Token);
//            Logger.d("base64Token  end");
//            HttpMethods.getInstance().uploadFileBase64(new MySubscriber<UploadImgEntity>(this) {
//
//                @Override
//                public void onHttpCompleted(HttpResult<UploadImgEntity> httpResult) {
//                    if (httpResult.isStatus()) {
//                        imgUrl.put(index,httpResult.getData().getHeadimg());
//                        currentUpdateImgIndex++;
//                        if(currentUpdateImgIndex<imgPaths.size()) {
//                            uploadFileBase64(currentUpdateImgIndex,imgPaths.get(currentUpdateImgIndex));
//                        }else{
//                            OkBtnNext();
//                        }
////                        Glide.with(context).load(MyApiService.IMG_BASE_URL2 + httpResult.getData().getHeadimg())
////                                .bitmapTransform(new GlideCircleTransform(context))
////                                .error(getResources().getDrawable(R.mipmap.default_user_head))
////                                .into(userHeadImg);
//                    } else {
//                        showToast("图片上傳失败");
//                    }
//                }
//
//                @Override
//                public void onHttpError(Throwable e) {
//                    showToast("图片上傳失败");
//                }
//            }, ukey, base64Token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//
//    private void stayTime() {
//        HttpMethods.getInstance().stayTime(new MySubscriber<ArrayList<CityEntity>>(this) {
//            @Override
//            public void onHttpCompleted(HttpResult<ArrayList<CityEntity>> arrayListHttpResult) {
//                if (arrayListHttpResult.isStatus()) {
//                    handlerStayTime(arrayListHttpResult.getData());
//                }
//            }
//
//            @Override
//            public void onHttpError(Throwable e) {
//            }
//        }, ukey);
//    }
//
//    private void whereCountryLayout() {
//        HttpMethods.getInstance().district(new MySubscriber<ArrayList<CityEntity>>(this) {
//            @Override
//            public void onHttpCompleted(HttpResult<ArrayList<CityEntity>> arrayListHttpResult) {
//                if (arrayListHttpResult.isStatus()) {
//                    handlerCountry(arrayListHttpResult.getData());
//                }
//            }
//
//            @Override
//            public void onHttpError(Throwable e) {
//            }
//        }, "0", ukey);
//    }

    private void nextActivity() {
        toActivityFinish(MainActivity.class);
    }

    private int currentUpdateImgIndex = 0;

    private void okBtn() {
        if (imgPaths.size() > 0) {
            uploadFileBase64(currentUpdateImgIndex, imgPaths.get(currentUpdateImgIndex));
        }
    }

    private void OkBtnNext() {

    }

//    private void xueliInfo(int type) {
//        isEduType = type;
//        if (eduTypeInfoEntity == null) {
//            HttpMethods.getInstance().getXueLiList(new MySubscriber<EduTypeInfoEntity>(this) {
//                @Override
//                public void onHttpCompleted(HttpResult<EduTypeInfoEntity> eduTypeInfoEntityHttpResult) {
//                    if (eduTypeInfoEntityHttpResult.isStatus()) {
//                        eduTypeInfoEntity = eduTypeInfoEntityHttpResult.getData();
//                        handlerEduType(eduTypeInfoEntity);
//                    }
//                }
//                @Override
//                public void onHttpError(Throwable e) {
//                }
//            }, ukey);
//        } else {
//            handlerEduType(eduTypeInfoEntity);
//        }
//    }
//
//    private void handlerEduType(final EduTypeInfoEntity eduTypeInfoEntity) {
//        if (isEduType == 0) {
//            initOptionPicker("学历", new OptionsPickerView.OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                    //返回的分别是三个级别的选中位置
//                    xueliTv.setText(eduTypeInfoEntity.getEdu().get(options1).getName());
//                    xueliTv.setTag(eduTypeInfoEntity.getEdu().get(options1).getId());
//                }
//            });
//            pvOptions.setPicker(eduTypeInfoEntity.getEdu());//一级选择器
//            pvOptions.show();
//        } else if(isEduType==1){
//            initOptionPicker("成绩类型", new OptionsPickerView.OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                    //返回的分别是三个级别的选中位置
//                    languageTv.setText(eduTypeInfoEntity.getType().get(options1).getName());
//                    languageTv.setTag(eduTypeInfoEntity.getType().get(options1).getId());
//                }
//            });
//            pvOptions.setPicker(eduTypeInfoEntity.getType());//一级选择器
//            pvOptions.show();
//        }else if(isEduType==2){
//            initOptionPicker("学历", new OptionsPickerView.OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                    //返回的分别是三个级别的选中位置
//                    whichDegreeTv.setText(eduTypeInfoEntity.getEdu().get(options1).getName());
//                    whichDegreeTv.setTag(eduTypeInfoEntity.getEdu().get(options1).getId());
//                }
//            });
//            pvOptions.setPicker(eduTypeInfoEntity.getEdu());//一级选择器
//            pvOptions.show();
//        }
//    }
//
//    private void whereInfo(String upid) {
//        Logger.d("upid :" + upid);
//        HttpMethods.getInstance().district(new MySubscriber<ArrayList<CityEntity>>(this) {
//            @Override
//            public void onHttpCompleted(HttpResult<ArrayList<CityEntity>> arrayListHttpResult) {
//                if (arrayListHttpResult.isStatus()) {
//                    handlerCity(arrayListHttpResult.getData());
//                }
//            }
//            @Override
//            public void onHttpError(Throwable e) {
//                isFirstSelectWheel = true;
//            }
//        }, upid, ukey);
//    }
//
//
//
//    private void handlerStayTime(final ArrayList<CityEntity> cityArr){
//        initOptionPicker("留学时间", new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
//                stayTimeTv.setText(cityArr.get(options1).getName());
//                stayTimeTv.setTag(cityArr.get(options1).getId());
//
//            }
//        });
//
//        pvOptions.setPicker(cityArr);//一级选择器
//
//        pvOptions.show();
//    }
//
//
//    private void handlerCountry(final ArrayList<CityEntity> cityArr){
//        initOptionPicker("地区", new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
//                whereCountryTv.setText(cityArr.get(options1).getName());
//                whereCountryTv.setTag(cityArr.get(options1).getId());
//
//            }
//        });
//
//        pvOptions.setPicker(cityArr);//一级选择器
//
//        pvOptions.show();
//    }
//
//    private boolean isFirstSelectWheel = true;
//    private String selectWhere = "";
//
//    private void handlerCity(final ArrayList<CityEntity> cityArr) {
//        initOptionPicker("地区", new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
//                if (isFirstSelectWheel) {
//                    isFirstSelectWheel = false;
//                    whereInfo(cityArr.get(options1).getId());
//                    selectWhere = cityArr.get(options1).getName();
//                } else {
//                    whereTv.setText(selectWhere + "," + cityArr.get(options1).getName());
//                    whereTv.setTag(cityArr.get(options1).getId());
//                }
//            }
//        });
//        pvOptions.setPicker(cityArr);//一级选择器
//
//        if (!isFirstSelectWheel) {
//            pvOptions.setOnDismissListener(new OnDismissListener() {
//                @Override
//                public void onDismiss(Object o) {
//                    isFirstSelectWheel = true;
//                }
//            });
//        }
//
//        pvOptions.show();
//    }
//
//    private OptionsPickerView pvOptions;
//
//    private void initOptionPicker(String wheelTitle, OptionsPickerView.OnOptionsSelectListener optionSelectListener) {//条件选择器初始化
//        pvOptions = new OptionsPickerView.Builder(this, optionSelectListener)
//                /*.setSubmitText("确定")
//                .setCancelText("取消")
//                .setTitleText("城市选择")
//                .setTitleSize(20)
//                .setSubCalSize(18)//确定取消按钮大小
//                .setTitleColor(Color.BLACK)
//                .setSubmitColor(Color.BLUE)
//                .setCancelColor(Color.BLUE)
//                .setBackgroundColor(Color.WHITE)
//                .setLinkage(false)//default true
//                .setCyclic(false, false, false)//循环与否
//                .setOutSideCancelable(false)//点击外部dismiss, default true
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                .setLabels("省", "市", "区")//设置选择的三级单位
//                .setLineSpacingMultiplier(2.0f) //设置两横线之间的间隔倍数（范围：1.2 - 2.0倍 文字高度）
//                .setDividerColor(Color.RED)//设置分割线的颜色
//                .isDialog(false)//是否设置为对话框模式
//                .setOutSideCancelable(false)//点击屏幕中控件外部范围，是否可以取消显示*/
//                .setTitleText(wheelTitle)
//                .setDividerType(WheelView.DividerType.FILL)
//                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
//                .setContentTextSize(20)//设置滚轮文字大小
//                .setSelectOptions(0, 1, 2)  //设置默认选中项
//                .setLineSpacingMultiplier(2.0f) //设置两横线之间的间隔倍数（范围：1.2 - 2.0倍 文字高度）
//                .build();
////            pvOptions.setPicker(options1Items);//一级选择器
////        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
////        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//    }

    private int mYear, mMonth, mDay;
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            dateEt.setText(mYear + "-" + mMonth + "-" + mDay);
        }
    };
}
