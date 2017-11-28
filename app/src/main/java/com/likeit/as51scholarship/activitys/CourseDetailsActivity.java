package com.likeit.as51scholarship.activitys;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.CourseSectionBean;
import com.likeit.as51scholarship.utils.ListScrollUtil;
import com.likeit.as51scholarship.utils.richtext.RichText;
import com.likeit.as51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CourseDetailsActivity extends Container implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView>, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.video_player_item_1)
    SuperVideoPlayer mSuperVideoPlayer;
    @BindView(R.id.play_btn)
    View mPlayBtnView;
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.course_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.tv_content)
    RichText tvContent;
    @BindView(R.id.ll_detail)
    LinearLayout ll_detail;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_play)
    TextView tv_play;
    @BindView(R.id.tv_free)
    Button tv_free;
    @BindView(R.id.tv_pay)
    Button tv_pay;
    private List<String> mDatas;
    private String video_url;
    private String content;
    private String id;
    @BindView(R.id.main_radio_group)
    RadioGroup main_radio_group;
    @BindView(R.id.radio_details)
    RadioButton rbDetails;
    @BindView(R.id.radio_news)
    RadioButton rbNews;
    @BindView(R.id.line_details)
    View line_details;
    @BindView(R.id.line_news)
    View line_news;
    @BindView(R.id.listView01)
    MyListview mListView;
    private String status = "1";
    private String vid;
    private List<CourseSectionBean> NewsData;
    private CourseSectionAdatpter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        video_url = intent.getStringExtra("video_url");
        content = intent.getStringExtra("content");
        id = intent.getStringExtra("mId");
        NewsData = new ArrayList<CourseSectionBean>();
        initView();
        initData();//詳情接口


    }

    private void initData() {
        String url = AppConfig.LIKEIT_COURSE_DETAILS;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("id", id);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    if ("1".equals(code)) {
                        tv_title.setText(object.optJSONObject("data").optString("title"));
                        tv_play.setText("播放：" + object.optJSONObject("data").optString("view") + "次");
                        String isfree = object.optJSONObject("data").optString("isfree");
                        vid = object.optJSONObject("data").optString("id");
                        if ("1".equals(isfree)) {
                            tv_free.setVisibility(View.VISIBLE);
                            tv_pay.setVisibility(View.GONE);
                        } else {
                            tv_free.setVisibility(View.GONE);
                            tv_pay.setVisibility(View.VISIBLE);
                        }
                        tvContent.setRichText(object.optJSONObject("data").optString("content"));
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

        //Uri uri = Uri.parse("http://bvideo.spriteapp.cn/video/2016/0704/577a4c29e1f14_wpd.mp4");
        mPlayBtnView.setOnClickListener(this);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        main_radio_group.setOnCheckedChangeListener(this);
        mAdapter=new CourseSectionAdatpter(mContext,NewsData);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                mAdapter.setDefSelect(position);
                //Person p = (Person) mListView.getItemAtPosition(position);
               // Toast.makeText(MainActivity.this, p.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.iv_header_left, R.id.play_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.play_btn:
                mPlayBtnView.setVisibility(View.GONE);
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setAutoHideController(false);
                Uri uri = Uri.parse(video_url);
                mSuperVideoPlayer.loadAndPlay(uri, 0);
                break;
        }
    }

    /**
     * 播放器的回调函数
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        /**
         * 播放器关闭按钮回调
         */
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.close();//关闭VideoView
            mPlayBtnView.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        /**
         * 播放器横竖屏切换回调
         */
        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        /**
         * 播放完成回调
         */
        @Override
        public void onPlayFinish() {

        }
    };

    /***
     * 旋转屏幕之后回调
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == mSuperVideoPlayer) return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
        }
    }

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
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

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_details:
                ll_detail.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
                status = "1";
                initData();
                line_news.setVisibility(View.GONE);
                line_details.setVisibility(View.VISIBLE);
                mPullToRefreshScrollView.onRefreshComplete();
                rbDetails.setChecked(true);
                rbNews.setChecked(false);
                break;
            case R.id.radio_news:
//                if(NewsData.size()==0){
//                    return;
//                }else{
//                    NewsData.clear();
//                }
                getListNew();
                status = "2";
                mListView.setVisibility(View.VISIBLE);
                ll_detail.setVisibility(View.GONE);
                line_news.setVisibility(View.VISIBLE);
                line_details.setVisibility(View.GONE);
//                newAdapter.addAll(NewsData, true);
//                newAdapter.notifyDataSetChanged();
                mPullToRefreshScrollView.onRefreshComplete();
                rbNews.setChecked(true);
                rbDetails.setChecked(false);
                //配置适配器
                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mAdapter.setDefSelect(0);//设置默认选中第一项
                ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
                break;
        }
    }

    private void getListNew() {
        String url = AppConfig.LIKEIT_COURSE_COURSELIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("vid", vid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            CourseSectionBean mCourseSectionBean = new CourseSectionBean();
                            mCourseSectionBean.setAuthor(object.optString("author"));
                            mCourseSectionBean.setId(object.optString("id"));
                            mCourseSectionBean.setTitle(object.optString("title"));
                            mCourseSectionBean.setCover(object.optString("cover"));
                            mCourseSectionBean.setCover_width(object.optString("cover_width"));
                            mCourseSectionBean.setCover_height(object.optString("cover_height"));
                            mCourseSectionBean.setDescription(object.optString("description"));
                            mCourseSectionBean.setInterval(object.optString("interval"));
                            mCourseSectionBean.setView(object.optString("view"));
                            mCourseSectionBean.setComment(object.optString("comment"));
                            mCourseSectionBean.setContent(object.optString("content"));
                            mCourseSectionBean.setVideo_url(object.optString("video_url"));
                            mCourseSectionBean.setFile_url(object.optString("file_url"));
                            mCourseSectionBean.setFile_name(object.optString("file_name"));
                            mCourseSectionBean.setCreate_time(object.optString("create_time"));
                            mCourseSectionBean.setTime(i + "1");
                            NewsData.add(mCourseSectionBean);
                        }
                        mAdapter.notifyDataSetChanged();
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
    class CourseSectionAdatpter extends BaseAdapter {

        private Context context;
        private List<CourseSectionBean> mCourseSectionBean;
        private ViewHolder holder;
        private int defItem;//声明默认选中的项

        public CourseSectionAdatpter(Context context, List<CourseSectionBean> mCourseSectionBean) {
            super();
            this.context = context;
            this.mCourseSectionBean = mCourseSectionBean;
        }

        @Override
        public int getCount() {
            return mCourseSectionBean.size();
        }

        @Override
        public Object getItem(int position) {
            return mCourseSectionBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        /**
         适配器中添加这个方法
         */
        public void setDefSelect(int position) {
            this.defItem = position;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_course_section_listview_items, null);
                holder = new ViewHolder();
                holder.item1 = (TextView) convertView.findViewById(R.id.tv_title);
                holder.item2 = (ImageView) convertView.findViewById(R.id.iv_play);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (defItem == position) {
                holder.item2.setBackgroundResource(R.mipmap.icon_selcet_play);
                holder.item1.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                holder.item2.setBackgroundResource(R.mipmap.icon_unselcet_play);
                holder.item1.setTextColor(getResources().getColor(R.color.defualt_textcolor_c));
            }
            //绑定数据
            CourseSectionBean courseSectionBean = mCourseSectionBean.get(position);
            holder.item1.setText("第"+courseSectionBean.getTime()+"课 "+courseSectionBean.getTitle());
            return convertView;
        }

        class ViewHolder {
            TextView item1;
            ImageView item2;
        }

    }

}
