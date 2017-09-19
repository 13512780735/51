package com.likeit.as51scholarship.activitys.my_center;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.activitys.SchoolDetailActivity;
import com.likeit.as51scholarship.adapters.UserCollectListAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.UserCollectBean;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.collect_listview)
    ListView mListView;
    private List<UserCollectBean> collectData;
    private UserCollectListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        collectData = new ArrayList<UserCollectBean>();
        initData();//收藏列表数据初始化
        showProgress("Loading...");
        initView();
    }


    private void initView() {

        tvHeader.setText("我的收藏");
        mAdapter = new UserCollectListAdapter(mContext, collectData);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = collectData.get(position).getName();
                String en_name = collectData.get(position).getEn_name();
                String img = collectData.get(position).getImg();
                String sid = collectData.get(position).getId();
                Intent intent = new Intent(mContext, SchoolDetailActivity.class);
                intent.putExtra("name", name);//英文名字
                intent.putExtra("en_name", en_name);//中文名字
                intent.putExtra("img", img);//图片
                intent.putExtra("sid", sid);//图片
                startActivity(intent);
            }
        });

    }

    @OnClick({R.id.backBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;

        }
    }

    private void initData() {
        String url = AppConfig.LIKEIT_MEMBER_COLLECT;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
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
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            UserCollectBean mUserCollectBean = new UserCollectBean();
                            mUserCollectBean.setId(object.optString("id"));
                            mUserCollectBean.setName(object.optString("name"));
                            mUserCollectBean.setEn_name(object.optString("en_name"));
                            mUserCollectBean.setCountry_id(object.optString("country_id"));
                            mUserCollectBean.setCountry_name(object.optString("country_name"));
                            mUserCollectBean.setRanking(object.optString("ranking"));
                            mUserCollectBean.setRate(object.optString("rate"));
                            mUserCollectBean.setScholarship(object.optString("scholarship"));
                            mUserCollectBean.setImg(object.optString("img"));
                            mUserCollectBean.setImg_width(object.optString("img_width"));
                            mUserCollectBean.setImg_height(object.optString("img_height"));
                            collectData.add(mUserCollectBean);
                        }
                        mAdapter.notifyDataSetChanged();
                    }else{
                        mListView.setVisibility(View.INVISIBLE);
                        rlNodata.setVisibility(View.VISIBLE);
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
}
