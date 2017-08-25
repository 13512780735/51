package com.likeit.a51scholarship.chat.message.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.chat.message.adapter.NewFriendsMsgAdapter;
import com.likeit.a51scholarship.chat.message.db.InviteMessgeDao;
import com.likeit.a51scholarship.chat.message.model.InviteMessage;

import java.util.Collections;
import java.util.List;


public class NewFriendsMsgActivity extends ChatBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);
        ListView listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);

    }

    public void back(View view) {
        finish();
    }
}
