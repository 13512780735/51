package com.likeit.as51scholarship.activitys.circlefragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.as51scholarship.adapters.CircleDetailsMemberAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.fragments.BaseFragment;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.circle_model.CircleMemberModel;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment03 extends BaseFragment {

    private String circleId;
    private ProgressDialog dialog;
    private MyListview mListView;
    private List<CircleMemberModel> memberData;
    private CircleDetailsMemberAdapter mAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_circle_fragment03;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        Intent intent = getActivity().getIntent();
        circleId = intent.getStringExtra("circleId");
        memberData = new ArrayList<CircleMemberModel>();
        initData();
        dialog.show();
        initView();

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        if (memberData == null || memberData.size() == 0) {
            return;
        } else {
            //  essayData.clear();
            mAdapter.addAll(memberData, true);
            //  memberData.clear();
            initData();
            dialog.show();
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mListView = findViewById(R.id.circle_member_listview03);
        mAdapter = new CircleDetailsMemberAdapter(getActivity(), memberData);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        String url = AppConfig.LIKEIT_GROUP_GET_MEMBER_LIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", circleId);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response);
                dialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            CircleMemberModel mCircleMemberModel = JSON.parseObject(object.toString(), CircleMemberModel.class);
                            memberData.add(mCircleMemberModel);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showS(getActivity(), message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                dialog.dismiss();
                ToastUtil.showS(getActivity(), "网络异常");
            }

            @Override

            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });
    }

    public static Fragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        UserDetailsFragment01 fragment = new UserDetailsFragment01();
        fragment.setArguments(args);
        return fragment;
    }

}
