package com.likeit.as51scholarship.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.newsfragment.NewFragment01;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.NewTabBean;
import com.likeit.as51scholarship.utils.AndroidWorkaround;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.likeit.as51scholarship.activitys.Container.setMiuiStatusBarDarkMode;


public class NewsListActivity extends FragmentActivity {
    @BindView(R.id.top_bar_back_img)
    ImageView topBarBackImg;
    @BindView(R.id.top_bar_title)
    TextView topBarTitle;
    @BindView(R.id.top_bar_right_img)
    ImageView topBarRightImg;
    @BindView(R.id.top_bar_edit_img)
    ImageView topBarEditImg;
    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private NewsListActivity mContext;
    private Window window;
    private List<NewTabBean> mDatas;
    private String ukey;
    private NewTabBean mNewTabBean;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_list);
        View main = getLayoutInflater().from(this).inflate(R.layout.activity_news_list, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        MyActivityManager.getInstance().addActivity(this);
        setContentView(main);
        mContext = this;
        setMiuiStatusBarDarkMode(this, true);
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        ukey = UtilPreference.getStringValue(mContext, "ukey");
        mDatas = new ArrayList<NewTabBean>();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        initTab();
        dialog.show();
    }

    private void initTab() {
        String page = "1";
        String url = AppConfig.LIKEIT_NEW_GETCATEGORY;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", page);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                dialog.dismiss();
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
                dialog.dismiss();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });
    }

    private void initView() {
        //设置TabLayout的模式
        Log.d("TAG2323", mDatas.toString());
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        final List<Fragment> mfragments = new ArrayList<Fragment>();
        for (int i = 0; i < mDatas.size(); i++) {
            NewFragment01 fragment = NewFragment01.newInstance(mDatas.get(i));
            Bundle bundle=new Bundle();
            bundle.putString("url",mDatas.get(i).getId());
            fragment.setArguments(bundle);
            mfragments.add(fragment);
        }
        for (int i = 0; i < mDatas.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText((CharSequence) mDatas.get(i).getTitle()));//添加tab选项
        }

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mfragments.get(position);
            }

            @Override
            public int getCount() {
                return mfragments.size();
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return (CharSequence) mDatas.get(position).getTitle();
            }
        };
        viewpager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(viewpager);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        viewpager.setCurrentItem(0);
    }


    @OnClick({R.id.top_bar_back_img, R.id.top_bar_right_img, R.id.top_bar_edit_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.top_bar_right_img:
                //oActivity(SearchInfoActivity.class);
                Intent intentSearchInfo = new Intent(this, SearchInfoActivity.class);
                intentSearchInfo.putExtra("key","2");
                startActivity(intentSearchInfo);
                break;
            case R.id.top_bar_edit_img:
                // toActivity(SendNewsActivity.class);

               // toActivity(SendNewsActivity.class);
                Intent intent=new Intent(mContext,SendNewsActivity.class);
                intent.putExtra("uId","1");
                startActivity(intent);
                break;
        }
    }
}
