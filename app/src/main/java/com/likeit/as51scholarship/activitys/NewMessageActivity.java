package com.likeit.as51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.adapters.MessageListAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.Messageabean;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewMessageActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.message_listview)
    ListView myListview;
    private List<Messageabean> messageData;
    private MessageListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        messageData=new ArrayList<Messageabean>();
        initData();
        showProgress("Loading...");
        initView();
    }

    private void initView() {
        tvHeader.setText("最新消息");
        mAdapter=new MessageListAdapter(mContext,messageData);
        myListview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link=messageData.get(position).getLink();
                Intent intent=new Intent(mContext,MessageWebActivity.class);
                intent.putExtra("link",link);
                startActivity(intent);
                Log.d("TAG","1111");
            }
        });

    }

    private void initData() {
        String url= AppConfig.LIKEIT_INDEX_NOTICE;
        RequestParams parmas=new RequestParams();
       // parmas.put("ukey",ukey);
        HttpUtil.post(url, parmas, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                disShowProgress();
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    String message=obj.optString("message");
                    if("1".equals(code)){
                        JSONArray array=obj.optJSONArray("data");
                        for(int i=0;i<array.length();i++){
                            JSONObject object=array.optJSONObject(i);
                            Messageabean mMessageabean=new Messageabean();
                            mMessageabean.setId(object.optString("id"));
                            mMessageabean.setTitle(object.optString("title"));
                            mMessageabean.setContent(object.optString("content"));
                            mMessageabean.setLink(object.optString("link"));
                            mMessageabean.setCreate_time(object.optString("create_time"));
                            messageData.add(mMessageabean);
                        }
                        mAdapter.notifyDataSetChanged();
                        Log.d("TAG",messageData.get(0).getId());
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
    @OnClick(R.id.backBtn)
    public void Onclick(View v){
        switch (v.getId()){
            case R.id.backBtn:
                onBackPressed();
                break;
        }
    }
}
