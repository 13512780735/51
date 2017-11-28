package com.likeit.as51scholarship.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.adapters.CircleDetailsEssayAdapter;
import com.likeit.as51scholarship.adapters.CircleDetailsMemberAdapter;
import com.likeit.as51scholarship.chat.message.utils.UserInfoCacheSvc;
import com.likeit.as51scholarship.chat.message.widget.DemoHelper;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.circle_model.CircleEssayModel;
import com.likeit.as51scholarship.model.circle_model.CircleMemberModel;
import com.likeit.as51scholarship.utils.ListScrollUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.likeit.as51scholarship.view.CircleImageView;
import com.likeit.as51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jaydenxiao.com.expandabletextview.ExpandableTextView;


public class CircleDetails01Activity extends Container implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.circle_details_attention)
    Button btAttention;//关注
    @BindView(R.id.circle_details_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.circle_details_attention01)
    Button btAttention01;//未关注
    @BindView(R.id.circle_details_Posted)
    Button btPosted;//发帖
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.circle_details_name)
    TextView tvName;
    @BindView(R.id.circle_details_posts)
    TextView tvPosts;
    @BindView(R.id.circle_details_membership)
    TextView tvMember;
    @BindView(R.id.circle_details_desc_tv)
    ExpandableTextView circleDetailsDescTv;
    @BindView(R.id.circle_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.rgTools)
    RadioGroup mRgTools;
    @BindView(R.id.listView01)
    MyListview mListview01;
    @BindView(R.id.listView02)
    MyListview mListview02;
    @BindView(R.id.listView03)
    MyListview mListview03;
    @BindView(R.id.nodata)
    TextView noData;


    private String circleId;
    private String circleTitle;
    private String circleDetail;
    private String circleLogo;
    private String circleMemberNum;
    private String circlePostNum;
    private String circleIsFollow;
    private CircleDetailsEssayAdapter mAdapter01;
    private ArrayList<CircleEssayModel> essayData01;
    private ArrayList<CircleMemberModel> memberData;
    private CircleDetailsMemberAdapter mAdapter03;
    private String rec;
    private String status="1";
    private ProgressDialog progressDialog;
    private String total;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_details01);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        circleDetail = intent.getStringExtra("circleDetail");
        circleId = intent.getStringExtra("circleId");
        circleTitle = intent.getStringExtra("circleTitle");
        circleLogo = intent.getStringExtra("circleLogo");
        circleMemberNum = intent.getStringExtra("circleMemberNum");
        circlePostNum = intent.getStringExtra("circlePostNum");
        circleIsFollow = intent.getStringExtra("circleIsFollow");
        rec = "0";
        initData(1);//全部/精華数据请求
        showProgress("Loading...");
        initView();
    }


    private void initView() {
        tvHeader.setText("圈子详情");
        tvName.setText(circleTitle);
        ImageLoader.getInstance().displayImage(circleLogo, ivAvatar);
        if ("1".equals(circleIsFollow)) {
            btAttention01.setVisibility(View.VISIBLE);
        } else {
            btAttention.setVisibility(View.VISIBLE);
        }
        tvPosts.setText("帖子:" + circlePostNum);
        tvMember.setText("成员数:" + circleMemberNum);
       // circleDetailsDescTv.setText("上次刷新时间上次刷新时间上次刷新时间上次刷新时间上次刷新时间上次刷新时间上次刷新时间上次刷新时间上次刷新时间上次刷新时间上次刷新时间");
        circleDetailsDescTv.setText(circleDetail);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        mRgTools.setOnCheckedChangeListener(this);
        //全部
        //精华
        essayData01 = new ArrayList<CircleEssayModel>();
        mAdapter01 = new CircleDetailsEssayAdapter(mContext, essayData01);
        //成员
        memberData = new ArrayList<CircleMemberModel>();
        mAdapter03 = new CircleDetailsMemberAdapter(mContext, memberData);
        //群组
        mListview01.setAdapter(mAdapter01);
        mAdapter01.notifyDataSetChanged();
        mAdapter03.setOnAddClickListener(new CircleDetailsMemberAdapter.onAddClickListener() {
            @Override
            public void onAddClick(int i) {
                final String easemob_id = memberData.get(i).getEasemob_id();
                final String nickName = memberData.get(i).getNickname();
                String headImg = memberData.get(i).getHeadimg();
                // ToastUtil.showS(mContext, "" + memberData.get(i).getEasemob_id());


                UserInfoCacheSvc.createOrUpdate(easemob_id, nickName, headImg);
                Log.d("TAG", "nickName" + nickName);
                // addContact(nickName);
                if (easemob_id.equals(UtilPreference.getStringValue(mContext, "easemob_id"))) {
                    ToastUtil.showS(mContext, "不能添加自己");
                } else {
                    new Thread(new Runnable() {
                        public void run() {

                            try {
                                String s = getResources().getString(R.string.Add_a_friend);
                                EMClient.getInstance().contactManager().addContact(easemob_id, s);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        //progressDialog.dismiss();
                                        String s1 = getResources().getString(R.string.send_successful);
                                        Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (final Exception e) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        // progressDialog.dismiss();
                                        String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                        Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });
        mAdapter01.setOnAddClickListener(new CircleDetailsEssayAdapter.onAddClickListener() {
            @Override
            public void onAddClick(int i) {
                final String nickName = essayData01.get(i).getUserinfo().getNickname();
                String headImg = essayData01.get(i).getUserinfo().getHeadimg();
                final String easemob_id = essayData01.get(i).getUserinfo().getEasemob_id();
                if (easemob_id.equals(UtilPreference.getStringValue(mContext, "easemob_id"))) {
                    ToastUtil.showS(mContext, "不能添加自己");
                } else {
                    new Thread(new Runnable() {
                        public void run() {

                            try {
                                String s = getResources().getString(R.string.Add_a_friend);
                                EMClient.getInstance().contactManager().addContact(easemob_id, s);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        String s1 = getResources().getString(R.string.send_successful);
                                        Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (final Exception e) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                        Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });
        mListview01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, PostActivity.class);
                intent.putExtra("id", essayData01.get(i).getId());
                intent.putExtra("title", essayData01.get(i).getTitle());
                intent.putExtra("content", essayData01.get(i).getContent());
                intent.putExtra("post_time", essayData01.get(i).getPost_time());
                intent.putExtra("nickname", essayData01.get(i).getUserinfo().getNickname());
                intent.putExtra("headimg", essayData01.get(i).getUserinfo().getHeadimg());
                intent.putExtra("easemob_id", essayData01.get(i).getUserinfo().getEasemob_id());
                intent.putExtra("isfriend", essayData01.get(i).getUserinfo().getIsfriend());
                startActivity(intent);
            }
        });
    }

    private void addContact(final String nickName) {
        if (EMClient.getInstance().getCurrentUser().equals(nickName)) {
            new EaseAlertDialog(this, R.string.not_add_myself).show();
            return;
        }

        if (DemoHelper.getInstance().getContactList().containsKey(nickName)) {
            //let the user know the contact already in your contact list
            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(nickName)) {
                new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
                return;
            }
            new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(nickName, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    @OnClick({R.id.backBtn, R.id.circle_details_Posted})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.circle_details_Posted:
                // toActivity(SendNewsActivity.class);
                Intent intent = new Intent(mContext, SendNewsActivity.class);
                intent.putExtra("uid", "2");
                intent.putExtra("gid", circleId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        // 获取当前时间并格式化
        mPullToRefreshScrollView.onRefreshComplete();
        String label = DateUtils.formatDateTime(
                mContext, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);
        // 设置刷新文本说明(刷新过程中)
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间" );
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel( "下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新" );
        if("1".equals(status)){
            refresh();

        }else if("2".equals(status)){
            refresh();
        }
        else if("3".equals(status)){
            refresh1();
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page++;
        Log.d("TAG",String.valueOf(page));
       //initData1(page);
        if("1".equals(status)){
            initData1(page);
        }else if("2".equals(status)){
            initData1(page);
        }
        else if("3".equals(status)){
            initData4(page);
        }
        int totalPage = Integer.valueOf(total) % 10;
        if (totalPage == 0) {
            totalPage = Integer.valueOf(total) / 10;
        } else {
            totalPage = Integer.valueOf(total) / 10 + 1;
        }
//        if (page <= totalPage) {// 上一次请求有数据
//            if("1".equals(status)){
//                initData1(page);
//            }else if("2".equals(status)){
//                initData1(page);
//            }
//            else if("3".equals(status)){
//                initData4(page);
//            }
//
//             }

    }

    private void task2() {

    }

    private void initData4(int page) {
        String url = AppConfig.LIKEIT_GROUP_GET_MEMBER_LIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", circleId);
        params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response);
                disShowProgress();
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
                        mAdapter03.notifyDataSetChanged();
                        ListScrollUtil. setListViewHeightBasedOnChildren(mListview02);
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常");
            }

            @Override

            public void onFinish() {
                super.onFinish();
                disShowProgress();
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void initData1(int page) {
        Log.d("TAG", circleId);
        String url = AppConfig.LIKEIT_GROUP_GETPOST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", circleId);
        params.put("rec", rec);
        params.put("page", String.valueOf(page));
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
                        total = obj.optString("total");
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            CircleEssayModel mCircleEssayModel = JSON.parseObject(object.toString(), CircleEssayModel.class);
                            essayData01.add(mCircleEssayModel);
                        }

                        mAdapter01.notifyDataSetChanged();
                        ListScrollUtil. setListViewHeightBasedOnChildren(mListview01);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
//                mListview.onRefreshComplete();
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void refresh1() {
        mAdapter03.addAll(memberData, true);
        initData3(1);
        showProgress("Loading...");
        mAdapter03.notifyDataSetChanged();
        ListScrollUtil. setListViewHeightBasedOnChildren(mListview02);
    }

    private void refresh() {
        mAdapter01.addAll(essayData01, true);
        initData(1);
        showProgress("Loading...");
        mAdapter01.notifyDataSetChanged();
        ListScrollUtil. setListViewHeightBasedOnChildren(mListview01);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            //全部
            case R.id.new_rball:
                mAdapter01.addAll(essayData01, true);
                // essayData01.clear();
                mAdapter01.notifyDataSetChanged();
                mListview02.setVisibility(View.GONE);
                mListview03.setVisibility(View.GONE);
                mListview01.setVisibility(View.VISIBLE);
                status = "1";
                rec = "0";
                page=1;
                initData(1);
                showProgress("Loading...");
                ListScrollUtil.setListViewHeightBasedOnChildren(mListview01);
                mPullToRefreshScrollView.onRefreshComplete();
                break;
            //精华
            case R.id.new_rbEssence:
                // essayData01.clear();
                mAdapter01.addAll(essayData01, true);
                mAdapter01.notifyDataSetChanged();
                mListview02.setVisibility(View.GONE);
                mListview03.setVisibility(View.GONE);
                mListview01.setVisibility(View.VISIBLE);
                status = "2";
                rec = "1";
                page=1;
                initData(1);
                showProgress("Loading...");
                ListScrollUtil.setListViewHeightBasedOnChildren(mListview01);
                mPullToRefreshScrollView.onRefreshComplete();
                break;
            //成员
            case R.id.new_rbmember:
                //memberData.clear();
                mAdapter03.addAll(memberData, true);
                mAdapter03.notifyDataSetChanged();
                mListview01.setVisibility(View.GONE);
                mListview03.setVisibility(View.GONE);
                mListview02.setVisibility(View.VISIBLE);
                status = "3";
                page=1;
                initData3(1);
                showProgress("Loading...");
                ListScrollUtil.setListViewHeightBasedOnChildren(mListview02);
                mListview02.setAdapter(mAdapter03);
                mPullToRefreshScrollView.onRefreshComplete();

                break;
            //群组
            case R.id.new_rbgroup:
                status = "4";
                mListview01.setVisibility(View.GONE);
                mListview03.setVisibility(View.GONE);
                mListview02.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 全部和精华请求
     */
    private void initData(int page) {
        Log.d("TAG", circleId);
        String url = AppConfig.LIKEIT_GROUP_GETPOST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", circleId);
        params.put("rec", rec);
        params.put("page", String.valueOf(page));
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
                        total = obj.optString("total");
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            CircleEssayModel mCircleEssayModel = JSON.parseObject(object.toString(), CircleEssayModel.class);
                            essayData01.add(mCircleEssayModel);
                        }

                        mAdapter01.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
//                mListview.onRefreshComplete();
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    /**
     * 成员数据请求
     */
    private void initData3(int page) {
        String url = AppConfig.LIKEIT_GROUP_GET_MEMBER_LIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", circleId);
        params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response);
                disShowProgress();
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
                        mAdapter03.notifyDataSetChanged();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常");
            }

            @Override

            public void onFinish() {
                super.onFinish();
                disShowProgress();
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }
}
