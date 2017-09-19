package com.likeit.as51scholarship.chat.message.message_chat_list;


import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.chat.message.adapter.NewFriendsMsgAdapter;
import com.likeit.as51scholarship.chat.message.db.InviteMessgeDao;
import com.likeit.as51scholarship.chat.message.model.InviteMessage;
import com.likeit.as51scholarship.fragments.MyBaseFragment;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends MyBaseFragment {


    @Override
    protected int setContentView() {
        return R.layout.fragment_message;
    }

    @Override
    protected void lazyLoad() {
        ListView listView = (ListView) findViewById(R.id.message_list);
        InviteMessgeDao dao = new InviteMessgeDao(getActivity());
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(getActivity(), 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
    }

}
