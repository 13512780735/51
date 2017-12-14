package com.likeit.as51scholarship.activitys;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.media.Retrofit.Api;
import com.likeit.as51scholarship.media.model.Ip;
import com.likeit.as51scholarship.media.model.Video;
import com.likeit.as51scholarship.media.view.VideoListLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveList01Activity extends Container implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.layout)
    VideoListLayout layout;
    List<Video.Data.DataBean> listData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fresh)
    SwipeRefreshLayout fresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_list01);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // tvHeader.setText("直播");
        fresh.setColorSchemeResources(R.color.rp_button_blue, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);
        fresh.setOnRefreshListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getData();
    }

    private void getData() {
        Api.getInstance(Ip.url_video).getService().Get_video().enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if (fresh != null) {
                    fresh.setRefreshing(false);
                }
                if (response.body() != null) {
                    listData = response.body().getData().getData();
                    for (int i = 0; i < listData.size(); i++) {
                        if (listData.get(i).getType() != 1) {//过滤掉不是视频的数据
                            listData.remove(i);
                        }
                    }
                    layout.setListData(listData);
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                //Log.i("video", t.getMessage());
                if (fresh != null) {
                    fresh.setRefreshing(false);
                }
            }
        });
    }

//    @OnClick({R.id.top_bar_back_img,})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.top_bar_back_img:
//                onBackPressed();
//                break;
//        }
//    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }


    }
}
