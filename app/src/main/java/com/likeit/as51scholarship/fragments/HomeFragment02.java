package com.likeit.as51scholarship.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.CircleDetails01Activity;
import com.likeit.as51scholarship.activitys.MainActivity;
import com.likeit.as51scholarship.activitys.SearchInfoActivity;
import com.likeit.as51scholarship.adapters.CircleGridViewAdapter;
import com.likeit.as51scholarship.adapters.CricleListViewAdapter;
import com.likeit.as51scholarship.adapters.GroupListFilterAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.circle_model.FollowCircleModel;
import com.likeit.as51scholarship.model.circle_model.GroupListFilterModel;
import com.likeit.as51scholarship.model.circle_model.GroupListModel;
import com.likeit.as51scholarship.utils.ListScrollUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.MyGridView;
import com.likeit.as51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment02 extends BaseFragment implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {


    private ImageView iv_header_left;
    private ImageView iv_header_right;
    private TextView tv_header;
    private MyGridView mGridView;
    private MyListview mListView;
    private ProgressDialog dialog;
    private CircleGridViewAdapter mGridviewAdapter;
    private List<Map<String, Object>> dataList;
    private List<Map<String, Object>> dataList01;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private Button ll_circle_filter;  //筛选
    private View layoutMenu;
    private ListView popMenuList;
    private List<GroupListFilterModel> listMenu;
    private PopupWindow popMenu;
    String tid;
    private List<FollowCircleModel> followCircleData;
    private List<GroupListModel> groupListData;
    private CricleListViewAdapter mGroupListAdapter;
    private CircleGridViewAdapter mFollowCircleAdapter;
    private String gid;
    private GroupListFilterAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment02;
    }

    @Override
    protected void lazyLoad() {
        tid = "0";
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        // 图片封装为一个数组
        //circleGridViewData = new ArrayList<CircleGridViewBean>();
        followCircleData = new ArrayList<FollowCircleModel>();
        groupListData = new ArrayList<GroupListModel>();
        listMenu = new ArrayList<GroupListFilterModel>();//筛选数据
//        //获取关注的圈子
        getFollowCircleData();
//        //获取全部圈子列表
        getGroupListData();
        //筛选数据
        getFilterData();
        dialog.show();
        initView();
        initListener();

    }

    private void getFilterData() {
        String url = AppConfig.LIKEIT_GROUP_GETTYPE;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    if ("1".equals(code)) {
                        JSONArray data = object.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = data.optJSONObject(i);
                            GroupListFilterModel mGroupListFilterModel = new GroupListFilterModel();
                            mGroupListFilterModel.setTid(obj.optString("tid"));
                            mGroupListFilterModel.setTitle(obj.optString("title"));
                            listMenu.add(mGroupListFilterModel);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showS(getActivity(), message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
       //refresh();
    }

    private void refresh() {
        //获取全部圈子列表
        mGroupListAdapter.addAll(groupListData, true);
        //groupListData.clear();
        getGroupListData();
        mGroupListAdapter.notifyDataSetChanged();
        //获取关注的圈子
        mFollowCircleAdapter.addAll(followCircleData, true);
        getFollowCircleData();
        mFollowCircleAdapter.notifyDataSetChanged();

        //dialog.show();

    }

    private void getFollowCircleData() {
        Log.d("TAG", "ukey-->" + ukey);
        String url = AppConfig.LIKEIT_GET_GROUP;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", "关注的圈子-->" + response);
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    if ("1".equals(code)) {
                        JSONArray data = object.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            FollowCircleModel mFollowCircleModel = new FollowCircleModel();
                            JSONObject obj = data.optJSONObject(i);
                            mFollowCircleModel.setId(obj.optString("id"));
                            mFollowCircleModel.setTitle(obj.optString("title"));
                            mFollowCircleModel.setDetail(obj.optString("detail"));
                            mFollowCircleModel.setLogo(obj.optString("logo"));
                            mFollowCircleModel.setMember_num(obj.optString("member_num"));
                            mFollowCircleModel.setPost_num(obj.optString("post_num"));
                            followCircleData.add(mFollowCircleModel);
                        }
                        mFollowCircleAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showS(getActivity(), message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable e) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });

    }

    private void getGroupListData() {
        String url = AppConfig.LIKEIT_GROUP_LIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("tid", tid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", "圈子列表-->" + response);
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    if ("1".equals(code)) {
                        JSONArray data = object.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            GroupListModel mGroupListModel = new GroupListModel();
                            JSONObject obj = data.optJSONObject(i);
                            mGroupListModel.setId(obj.optString("id"));
                            mGroupListModel.setTitle(obj.optString("title"));
                            mGroupListModel.setDetail(obj.optString("detail"));
                            mGroupListModel.setLogo(obj.optString("logo"));
                            mGroupListModel.setMember_num(obj.optString("member_num"));
                            mGroupListModel.setPost_num(obj.optString("post_num"));
                            mGroupListModel.setIsfollow(obj.optString("isfollow"));
                            groupListData.add(mGroupListModel);
                        }
                        mGroupListAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showS(getActivity(), message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });
    }


    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.ll_circle_scrollview);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        iv_header_left = findViewById(R.id.iv_header_left);
        iv_header_right = findViewById(R.id.iv_header_right);
        tv_header = findViewById(R.id.tv_header);
        tv_header.setText("圈子");
        iv_header_left.setImageResource(R.mipmap.nav_icon_user);
        iv_header_right.setImageResource(R.mipmap.nav_icon_search_sel);
        mGridView = findViewById(R.id.circle_gridview);
        mListView = findViewById(R.id.circle_listview);
        ll_circle_filter = findViewById(R.id.ll_circle_filter);
        /**
         * 关注的圈子
         */
        mFollowCircleAdapter = new CircleGridViewAdapter(getActivity(), followCircleData);
        mGridView.setAdapter(mFollowCircleAdapter);
        mFollowCircleAdapter.notifyDataSetChanged();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // toActivity(CircleDetailsActivity.class);
                String circleId = followCircleData.get(position).getId();
                String circleTitle = followCircleData.get(position).getTitle();
                String circleDetail = followCircleData.get(position).getDetail();
                String circleLogo = followCircleData.get(position).getLogo();
                String circleMemberNum = followCircleData.get(position).getMember_num();
                String circlePostNum = followCircleData.get(position).getPost_num();
                String circleIsFollow = "1";
                Intent intentDetails = new Intent(getActivity(), CircleDetails01Activity.class);
                intentDetails.putExtra("circleId", circleId);
                intentDetails.putExtra("circleTitle", circleTitle);
                intentDetails.putExtra("circleDetail", circleDetail);
                intentDetails.putExtra("circleLogo", circleLogo);
                intentDetails.putExtra("circleMemberNum", circleMemberNum);
                intentDetails.putExtra("circlePostNum", circlePostNum);
                intentDetails.putExtra("circleIsFollow", circleIsFollow);
                startActivity(intentDetails);
            }
        });
        /**
         * 全部圈子
         */
        mGroupListAdapter = new CricleListViewAdapter(getActivity(), groupListData);
        //配置适配器
        mListView.setAdapter(mGroupListAdapter);
        mGroupListAdapter.notifyDataSetChanged();
        ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String circleId = groupListData.get(position).getId();
                String circleTitle = groupListData.get(position).getTitle();
                String circleDetail = groupListData.get(position).getDetail();
                String circleLogo = groupListData.get(position).getLogo();
                String circleMemberNum = groupListData.get(position).getMember_num();
                String circlePostNum = groupListData.get(position).getPost_num();
                String circleIsFollow = groupListData.get(position).getIsfollow();
                Intent intentDetails = new Intent(getActivity(), CircleDetails01Activity.class);
                intentDetails.putExtra("circleId", circleId);
                intentDetails.putExtra("circleTitle", circleTitle);
                intentDetails.putExtra("circleDetail", circleDetail);
                intentDetails.putExtra("circleLogo", circleLogo);
                intentDetails.putExtra("circleMemberNum", circleMemberNum);
                intentDetails.putExtra("circlePostNum", circlePostNum);
                intentDetails.putExtra("circleIsFollow", circleIsFollow);
                startActivity(intentDetails);
            }
        });
        mGroupListAdapter.setOnEnFollowClickListener(new CricleListViewAdapter.onBtnEnFollowClickListener() {
            @Override
            public void onEnFollowClick(int i) {
                gid = groupListData.get(i).getId();
                ToastUtil.showS(getActivity(), i + "");
                refreshList();
            }
        });
        mGroupListAdapter.setOnFollowClickListener(new CricleListViewAdapter.onBtnFollowClickListener() {
            @Override
            public void onFollowClick(int i) {
                gid = groupListData.get(i).getId();
                ToastUtil.showS(getActivity(), i + "");
                refreshList();
            }
        });

    }

    private void refreshList() {
        String url = AppConfig.LIKEIT_GROUP_FOLLOW_GROUP;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", gid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(getActivity(), message);
                        refresh();
                        mPullToRefreshScrollView.onRefreshComplete();
                    } else {
                        ToastUtil.showS(getActivity(), message);
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

    private void initListener() {
        iv_header_left.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
        ll_circle_filter.setOnClickListener(this);
    }

    @Override
    public boolean haveEventBus() {
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.refresh();
                break;
            case R.id.iv_header_right:
                toActivity(SearchInfoActivity.class);
                break;
            case R.id.ll_circle_filter:
                selectMenu();
                break;
        }
    }

    private void selectMenu() {
        if (popMenu != null && popMenu.isShowing()) {
            popMenu.dismiss();
        } else {

            layoutMenu = getActivity().getLayoutInflater().inflate(
                    R.layout.operationinto_popmenulist, null);
            popMenuList = (ListView) layoutMenu
                    .findViewById(R.id.menulist);

            // 创建ArrayAdapter
            adapter = new GroupListFilterAdapter(
                    getActivity(),
                    listMenu);
            // 绑定适配器
            popMenuList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // 点击listview中item的处理
            popMenuList
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            // 改变顶部对应TextView值
                            tid = listMenu.get(position).getTid();
                            // 隐藏弹出窗口
                            if (popMenu != null && popMenu.isShowing()) {
                                popMenu.dismiss();
                            }
                            refresh();
                        }
                    });

            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟tvLeft一样
            popMenu = new PopupWindow(layoutMenu, ll_circle_filter.getWidth() * 3 / 2,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            popMenu.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.filter_bg));
            popMenu.setAnimationStyle(R.style.PopupAnimation);
            popMenu.update();
            popMenu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popMenu.setTouchable(true); // 设置popupwindow可点击
            popMenu.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popMenu.setFocusable(true); // 获取焦点

            // 设置popupwindow的位置（相对tvLeft的位置）
            int topBarHeight = ll_circle_filter.getBottom();
            popMenu.showAsDropDown(ll_circle_filter, 0, 5);
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

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        refresh();
        ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        refresh();
        ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
        mPullToRefreshScrollView.onRefreshComplete();
    }
}
