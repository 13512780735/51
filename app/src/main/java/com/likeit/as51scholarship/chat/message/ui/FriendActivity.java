package com.likeit.as51scholarship.chat.message.ui;

import android.os.Bundle;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.chat.message.fragment.ContactListFragment;


public class FriendActivity extends ChatBaseActivity {

    private ContactListFragment contactListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        contactListFragment = new ContactListFragment();
        contactListFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, contactListFragment).commit();
    }
}
