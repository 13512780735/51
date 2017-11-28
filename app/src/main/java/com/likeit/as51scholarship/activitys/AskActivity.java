package com.likeit.as51scholarship.activitys;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.NewTabBean;
import com.likeit.as51scholarship.model.question.QuestionCategoryBean;
import com.likeit.as51scholarship.model.userapply.UserDistrictBean;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.MyGridView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AskActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.content_et)
    EditText content_et;
    @BindView(R.id.question_catetory)
    MyGridView myGridView01;//分類
    @BindView(R.id.question_country)
    MyGridView myGridView02;//国家
    private List<QuestionCategoryBean> categoryData;
    private List<UserDistrictBean> countryData;
    private int selectPosition = -1;//用于记录用户选择的变量
    private AskCatetoryAdapter mAdapter01;
    private String cid;// 分类ID
    private int selectPosition01 = -1;
    private AskCountryAdapter mAdapter02;
    private String cid02;//国家ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        ButterKnife.bind(this);
        categoryData = new ArrayList<QuestionCategoryBean>();
        countryData = new ArrayList<UserDistrictBean>();
        initcountry(); //国家
        initcategory(); //分类
        showProgress("Loading...");
        initView();
    }

    private void initcategory() {
        String url = AppConfig.LIKEIT_QUESTION_GETCATEGORY;
        RequestParams param = new RequestParams();
        param.put("ukey", ukey);
        HttpUtil.post(url, param, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    Log.d("TAG", response);
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            QuestionCategoryBean mQuestionCategoryBean = new QuestionCategoryBean();
                            mQuestionCategoryBean.setId(object.optString("id"));
                            mQuestionCategoryBean.setTitle(object.optString("title"));
                            categoryData.add(mQuestionCategoryBean);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }

        });
    }

    private void initcountry() {
        String url = AppConfig.LIKEIT_MEMBER_EDIT_DISTRICT;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            UserDistrictBean mUserDistrictBean = new UserDistrictBean();
                            mUserDistrictBean.setId(object.optString("id"));
                            mUserDistrictBean.setName(object.optString("name"));
                            countryData.add(mUserDistrictBean);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }
        });
    }

    private void initView() {
        tvHeader.setText("提问");
        tvRight.setText("发布");
        mAdapter01 = new AskCatetoryAdapter(mContext, categoryData);
        myGridView01.setAdapter(mAdapter01);
        mAdapter01.notifyDataSetChanged();
        myGridView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TAG", position + "");
                cid = categoryData.get(position).getId();
                selectPosition = position;
                mAdapter01.notifyDataSetChanged();
            }
        });
        mAdapter02 = new AskCountryAdapter(mContext, countryData);
        myGridView02.setAdapter(mAdapter02);
        mAdapter02.notifyDataSetChanged();
        myGridView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TAG", position + "");
                cid02 = countryData.get(position).getId();
                selectPosition01 = position;
                mAdapter02.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.backBtn, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.tv_right:
                initSend();
                break;
        }
    }

    private void initSend() {
        String content = content_et.getText().toString();
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(cid) || TextUtils.isEmpty(cid02)) {
            ToastUtil.showS(mContext, "内容或者分类标签不能为空!");
            return;
        }
        String url = AppConfig.LIKEIT_QUESTION_ADDQUESTION;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("content", content);
        params.put("cid", cid);
        params.put("countryid", cid02);
        showProgress("Loading...");
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        onBackPressed();
                    } else {
                        ToastUtil.showS(mContext, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }
        });
    }


    public class AskCatetoryAdapter extends BaseAdapter {

        Context context;
        List<QuestionCategoryBean> brandsList;
        LayoutInflater mInflater;

        public AskCatetoryAdapter(Context context, List<QuestionCategoryBean> mList) {
            this.context = context;
            this.brandsList = mList;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return brandsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.school_filter_gridview_items02, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.select = (RadioButton) convertView.findViewById(R.id.id_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            WindowManager wm = (WindowManager) getApplication()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            viewHolder.select.setWidth(Integer.valueOf(width / 4) - 20);
            viewHolder.select.setGravity(Gravity.CENTER);
            viewHolder.select.setText(brandsList.get(position).getTitle());

            if (selectPosition == position) {
                viewHolder.select.setChecked(true);
                viewHolder.select.setBackgroundResource(R.drawable.goods_attr_selected_shape);
                viewHolder.select.setTextColor(Color.WHITE);
            } else {
                viewHolder.select.setChecked(false);
                viewHolder.select.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
                viewHolder.select.setTextColor(Color.DKGRAY);
            }
            return convertView;
        }

        public class ViewHolder {
            RadioButton select;
        }
    }

    public class AskCountryAdapter extends BaseAdapter {

        Context context;
        List<UserDistrictBean> brandsList;
        LayoutInflater mInflater;

        public AskCountryAdapter(Context context, List<UserDistrictBean> mList) {
            this.context = context;
            this.brandsList = mList;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return brandsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.school_filter_gridview_items03, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.select = (RadioButton) convertView.findViewById(R.id.id_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            WindowManager wm = (WindowManager) getApplication()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            viewHolder.select.setText(brandsList.get(position).getName());
            viewHolder.select.setWidth(Integer.valueOf(width / 4) - 20);
            viewHolder.select.setGravity(Gravity.CENTER);
            if (selectPosition01 == position) {
                viewHolder.select.setChecked(true);
                viewHolder.select.setBackgroundResource(R.drawable.goods_attr_selected_shape);
                viewHolder.select.setTextColor(Color.WHITE);
            } else {
                viewHolder.select.setChecked(false);
                viewHolder.select.setBackgroundResource(R.drawable.goods_attr_unselected_shape);
                viewHolder.select.setTextColor(Color.DKGRAY);
            }
            return convertView;
        }

        public class ViewHolder {
            RadioButton select;
        }
    }
}
