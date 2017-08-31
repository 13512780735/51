package com.likeit.a51scholarship.fragments;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment05 extends BaseFragment implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {
    private ImageView iv_header_left;
    private TextView tv_header;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private GridView mGridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.icon_tools_dictionary, R.mipmap.icon_tools_gpa,
            R.mipmap.icon_tools_cnh, R.mipmap.icon_tools_time, R.mipmap.icon_tools_memo,
            R.mipmap.icon_tools_diary};
    private String[] iconName = {"英语词典", "我的GPA", "汇率转换", "时间转换", "备忘录", "留学日记"};
    private String[] iconDetails = {"英语词汇量专业测评", "多种通用GPA计算方法", "热门旅游国家及欧洲各国", "世界时间单位相互转换", "记录每天易忘事宜", "帮你保存留学的美好记忆"};


    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment05;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initListener();
        //2.适配器加载数据源


    }

    private void initView() {
        iv_header_left = findViewById(R.id.iv_header_left);
        tv_header = findViewById(R.id.tv_header);
        tv_header.setText("小工具");
        iv_header_left.setImageResource(R.mipmap.nav_icon_user);
        mPullToRefreshScrollView = findViewById(R.id.ll_tool_scrollview);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        mGridView = findViewById(R.id.ll_tools_gridview);
        dataList = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"img", "name", "detail"};
        int[] to = {R.id.iv_tools_avatar, R.id.tv_tools_name, R.id.tv_tools_details};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.item_ll_tools_gridview, from, to);
        //配置适配器
        mGridView.setAdapter(simpleAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showS(getActivity(),"敬请期待！");
            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            map.put("detail", iconDetails[i]);
            dataList.add(map);
        }
        return dataList;
    }

    private void initListener() {
        iv_header_left.setOnClickListener(this);
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
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }
}
