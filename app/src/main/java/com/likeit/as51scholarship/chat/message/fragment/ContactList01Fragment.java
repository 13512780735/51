package com.likeit.as51scholarship.chat.message.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.NetUtils;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.MainActivity;
import com.likeit.as51scholarship.chat.message.adapter.UserListAdapter;
import com.likeit.as51scholarship.chat.message.db.InviteMessgeDao;
import com.likeit.as51scholarship.chat.message.model.EaseUserBean;
import com.likeit.as51scholarship.chat.message.ui.AddContactActivity;
import com.likeit.as51scholarship.chat.message.ui.ChatActivity;
import com.likeit.as51scholarship.chat.message.widget.ContactItemView;
import com.likeit.as51scholarship.chat.message.widget.DemoHelper;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactList01Fragment extends EaseContactListFragment {

    private static final String TAG = ContactList01Fragment.class.getSimpleName();
    private ContactList01Fragment.ContactSyncListener contactSyncListener;
    private ContactList01Fragment.BlackListSyncListener blackListSyncListener;
    private ContactList01Fragment.ContactInfoSyncListener contactInfoSyncListener;
    private View loadingView;
    private ContactItemView applicationItem;
    private InviteMessgeDao inviteMessgeDao;
    private String ukey;
    private EaseUserBean mEaseUserBean;
    private List<EaseUserBean> userData;
    private UserListAdapter adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_contact_list01, container, false);
//    }


    class ContactSyncListener implements DemoHelper.DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d("TAG", "on contact list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            if(success){
                                loadingView.setVisibility(View.GONE);
                                refresh();
                            }else{
                                String s1 = getResources().getString(R.string.get_failed_please_check);
                                Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
                                loadingView.setVisibility(View.GONE);
                            }
                        }

                    });
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {
        titleBar.setRightImageResource(R.drawable.ease_blue_add);
        titleBar.setLeftImageResource(R.drawable.icon_back);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), AddContactActivity.class));
                NetUtils.hasDataConnection(getActivity());
            }
        });
        titleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                getActivity().finish();
            }
        });
        //设置联系人数据
//        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
//        if (m instanceof Hashtable<?, ?>) {
//            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>)m).clone();
//        }
//        setContactsMap(m);
        initData();
        super.setUpView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                EaseUser user = (EaseUser)listView.getItemAtPosition(position);
//                if (user != null) {
//                    String username = user.getUsername();
//                    // demo中直接进入聊天页面，实际一般是进入用户详情页
//                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
//                }
                if (userData != null) {
                    String nickname = userData.get(position - 1).getNickname();
                    String headimg = userData.get(position - 1).getHeadimg();
                    EaseUserBean mEaseUserBean1=userData.get(position);
                    String easemob_id = userData.get(position - 1).getEasemob_id();
                    String uid = userData.get(position - 1).getUid();
                    //设置要发送出去的头像
//                    mEaseUserBean.setEasemob_id(userData.get(position-1).getEasemob_id());
//                    mEaseUserBean.setHeadimg(userData.get(position-1).getHeadimg());
//                    mEaseUserBean.setNickname(userData.get(position-1).getNickname());
//                    mEaseUserBean.setUid(userData.get(position-1).getUid());
//                    SharedPreferencesUtils.setParam(getActivity(),AppConfig.USER_NAME,userData.get(position - 1).getNickname());
//                    SharedPreferencesUtils.setParam(getActivity(),AppConfig.USER_HEAD_IMG,userData.get(position - 1).getHeadimg());
//                    // demo中直接进入聊天页面，实际一般是进入用户详情页
//                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", (Parcelable) mEaseUserBean1)
//
//                            );
                    Intent intent=new Intent(getActivity(),ChatActivity.class);
                    //传入参数
                    Bundle args=new Bundle();
                    args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    args.putString(EaseConstant.EXTRA_USER_ID,easemob_id);
                    args.putString(EaseConstant.EXTRA_USER_NAME,nickname);
                    args.putString(EaseConstant.EXTRA_USER_IMG,headimg);
                    intent.putExtra("conversation",args);

                    startActivity(intent);
                }

            }

        });


        // 进入添加好友页
        titleBar.getRightLayout().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });


        contactSyncListener = new ContactList01Fragment.ContactSyncListener();
        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);

        blackListSyncListener = new ContactList01Fragment.BlackListSyncListener();
        DemoHelper.getInstance().addSyncBlackListListener(blackListSyncListener);

        contactInfoSyncListener = new ContactList01Fragment.ContactInfoSyncListener();
        DemoHelper.getInstance().getUserProfileManager().addSyncContactInfoListener(contactInfoSyncListener);

        if (DemoHelper.getInstance().isContactsSyncedWithServer()) {
          //  loadingView.setVisibility(View.GONE);
        } else if (DemoHelper.getInstance().isSyncingContactsWithServer()) {
          //  loadingView.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        ukey= UtilPreference.getStringValue(getActivity(),"ukey");
        String url= AppConfig.LIKEIT_GET_EASE_FRIEND;
        RequestParams params=new RequestParams();
         params.put("ukey",ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject object=new JSONObject(response);
                    String code=object.optString("code");
                    String message=object.optString("message");
                    if("1".equals(code)){
                        JSONArray array=object.optJSONArray("data");
                        for(int i=0;i<array.length();i++){
                            JSONObject obj=array.optJSONObject(i);

                            mEaseUserBean= new EaseUserBean();
                            mEaseUserBean.setEasemob_id(obj.optString("easemob_id"));
                            mEaseUserBean.setNickname(obj.optString("nickname"));
                            mEaseUserBean.setHeadimg(obj.optString("headimg"));
                            mEaseUserBean.setUid(obj.optString("uid"));
                            //  SharedPreferencesUtils.setParam(mContext,"userBean",mEaseUserBean);
                            userData.add(mEaseUserBean);
                        }
                        adapter = new UserListAdapter(getActivity(), userData);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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

    class BlackListSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(boolean success) {
            getActivity().runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    refresh();
                }
            });
        }

    }

    class ContactInfoSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contactinfo list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    loadingView.setVisibility(View.GONE);
                    if(success){
                        refresh();
                    }
                }
            });
        }

    }
}
