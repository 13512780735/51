package com.likeit.a51scholarship.activitys.circlefragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.adapters.CircleDetailsEssayAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.fragments.BaseFragment;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.circle_model.CircleEssayModel;
import com.likeit.a51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment02 extends BaseFragment {

    private String circleId;
    private ProgressDialog dialog;
    private List<CircleEssayModel> essayData;
    private CircleDetailsEssayAdapter mAdapter;
    private MyListview mListView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_circle_fragment02;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        Intent intent=getActivity().getIntent();
        circleId = intent.getStringExtra("circleId");
        essayData=new ArrayList<CircleEssayModel>();
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
        if (essayData == null || essayData.size() == 0) {
            return;
        } else {
            //  essayData.clear();
            mAdapter.addAll(essayData, true);
            initData();
        dialog.show();
        mAdapter.notifyDataSetChanged();}
    }

    private void initView() {
        mListView=findViewById(R.id.circle_essay_listview02);
        mAdapter=new CircleDetailsEssayAdapter(getActivity(),essayData);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        Log.d("TAG",circleId);
        String url= AppConfig.LIKEIT_GROUP_GETPOST;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("gid",circleId);
        params.put("rec","1");
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                dialog.dismiss();
                Log.d("TAG",response);
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    String message=obj.optString("message");
                    if("1".equals(code)){
                        JSONArray array=obj.optJSONArray("data");
                        for(int i=0;i<array.length();i++){
                            JSONObject object=array.optJSONObject(i);
                            CircleEssayModel mCircleEssayModel= JSON.parseObject(object.toString(),CircleEssayModel.class);
                            essayData.add(mCircleEssayModel);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
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
