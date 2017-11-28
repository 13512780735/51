package com.likeit.as51scholarship.chat.message.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.chat.message.DemoModel;
import com.likeit.as51scholarship.chat.message.adapter.NewFriendsMsgAdapter;
import com.likeit.as51scholarship.chat.message.db.InviteMessgeDao;
import com.likeit.as51scholarship.chat.message.model.InviteMessage;
import com.likeit.as51scholarship.chat.message.utils.UserInfoCacheSvc;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class NewFriendsMsgActivity extends ChatBaseActivity {

    private NewFriendsMsgAdapter adapter;
    private InviteMessgeDao dao;
    private ListView listView;
    private Map<String, EaseUser> contactList;
    private DemoModel demoModel = null;
    private String ukey;
    private String username;
    private String userPic;
    private String userId;
    private String hxIdFrom;
    private ProgressDialog dialog;
    private List<InviteMessage> msgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);
        ListView listView = (ListView) findViewById(R.id.list);
        dao = new InviteMessgeDao(this);
        msgs = dao.getMessagesList();
        if (msgs.size()==0) {
            Collections.reverse(msgs);
            adapter = new NewFriendsMsgAdapter(this, 1, msgs);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return;

        }else{
            String username = msgs.get(0).getFrom();
            Log.d("TAG323", username);
            initUser(username);
            dao.saveUnreadMessageCount(0);
        }
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
        adapter.notifyDataSetChanged();
    }
    private void initUser(String userName) {
        String url = AppConfig.LIKEIT_GET_EASEMOB_USERINFO;
        ukey = UtilPreference.getStringValue(this, "ukey");
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("easemob_id", userName);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG6262", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    if ("1".equals(code)) {
                        JSONObject obj = object.optJSONObject("data");
                        username = obj.optString("nickname");
                        userPic = obj.optString("headimg");
                        userId = obj.optString("uid");
                        hxIdFrom = obj.optString("easemob_id");
                        Log.d("TAG", "helper接收到的用户名：" + username + "helper接收到的id：" + hxIdFrom + "helper头像：" + userPic);
                        UserInfoCacheSvc.createOrUpdate(hxIdFrom, username,userPic);
                        //UserInfoCacheSvc.createOrUpdate(hxIdFrom, userName, userPic);
//                        EaseUser easeUser = new EaseUser(hxIdFrom);
//                        easeUser.setAvatar(userPic);
//                        easeUser.setNick(username);
//                        //存入内存
//                        getContactList();
//                        contactList.put(hxIdFrom, easeUser);
//                        //存入db
//                        UserDao dao = new UserDao(getActivity());
//                        List<EaseUser> users = new ArrayList<EaseUser>();
//                        users.add(easeUser);
//                        dao.saveContactList(users);
//                        //mHandler.sendEmptyMessage(0);


                    }
                    msgs = dao.getMessagesList();
                    Collections.reverse(msgs);
                    adapter = new NewFriendsMsgAdapter(NewFriendsMsgActivity.this, 1, msgs);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }

    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            contactList = demoModel.getContactList();
        }

        // return a empty non-null object to avoid app crash
        if (contactList == null) {
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }

    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }
    public void back(View view) {
        finish();
    }
}
