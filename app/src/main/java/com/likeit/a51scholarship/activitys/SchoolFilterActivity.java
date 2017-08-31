package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.SchoolFilterAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.SchoolAttributeNameVo;
import com.likeit.a51scholarship.model.SchoolAttributeVo;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.view.MessageEvent;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SchoolFilterActivity extends Container {
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_totle)
    TextView tvTotle;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.school_filter_listview)
    ListView mListView;
    List<SchoolAttributeNameVo> data;
    private SchoolFilterAdapter mAdapter;
    private String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_filter);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initData();//初始化筛选数据
        initTotal();//初始化总数
        initView();
    }

    private void initTotal() {
        String url=AppConfig.LIKEIT_SCHOOL_FILTR_COUNT;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
//        params.put("stage",ukey);
//        params.put("country",ukey);
//        params.put("area",ukey);
//        params.put("lang",ukey);
//        params.put("nature",ukey);
//        params.put("style",ukey);
//        params.put("toefl",ukey);
//        params.put("toeic",ukey);
//        params.put("yasi",ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    String message=obj.optString("message");
                    if("1".equals(code)){
                        total=obj.getJSONObject("data").optString("total");
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
        tvHeader.setText("筛选院校");
        tvTotle.setText("共"+total+"个高校符合条件>>");
        data = new ArrayList<SchoolAttributeNameVo>();
        mAdapter = new SchoolFilterAdapter(mContext, data);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        String url = AppConfig.LIKEIT_SCHOOL_FILTR_PARAM;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getString("message");
                    String code = obj.getString("code");
                    if ("1".equals(code)) {
                        JSONArray json = obj.optJSONArray("data");
                        refreshAttrs(json);
                        Log.d("TAG", "data-->" + json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable e) {
                ToastUtil.showS(mContext,"网络异常！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private void refreshAttrs(JSONArray json) {
        data.clear();
        for (int i = 0; i < json.length(); i++) {
            SchoolAttributeNameVo schoolAttriName = new SchoolAttributeNameVo();
            JSONObject obj = json.optJSONObject(i);
            schoolAttriName.setName(obj.optString("title"));
            List<SchoolAttributeVo> list = new ArrayList<SchoolAttributeVo>();
            JSONArray array = obj.optJSONArray("type");
            for (int j = 0; j < array.length(); j++) {
                JSONObject object = array.optJSONObject(j);
                SchoolAttributeVo vo = new SchoolAttributeVo();
                vo.setAttr_name(object.optString("name"));
                vo.setAttr_id(object.optString("id"));
                list.add(vo);
            }
            schoolAttriName.setSchoolAttr(list);
            data.add(schoolAttriName);
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.backBtn, R.id.tv_reset, R.id.tv_totle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.tv_reset:
                //onBackPressed();
                break;
            case R.id.tv_totle:
                Intent intentSchool = new Intent(mContext, SearchSchoolActivity.class);
                startActivity(intentSchool);
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        //tv_message.setText(messageEvent.getMessage());
        ToastUtil.showS(mContext,messageEvent.getMessage());
        Log.d("TAG",messageEvent.getMessage());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册：一般在事件view销毁的时候
        EventBus.getDefault().unregister(this);
    }
}
