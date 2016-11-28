package vulan.com.chatapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vulan.com.chatapp.R;
import vulan.com.chatapp.adapter.ChatAdapter;
import vulan.com.chatapp.entity.MessageUser;
import vulan.com.chatapp.util.ChatBroadcastReceiver;
import vulan.com.chatapp.util.FakeContainer;
import vulan.com.chatapp.util.MessageDataSource;
import vulan.com.chatapp.widget.LinearItemDecoration;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, MessageDataSource.MessageCallback {

    private List<MessageUser> mMesseageList;
    private RecyclerView mRecyclerChat;
    private EditText mEditText;
    private ImageView mButtonSend;
    private ChatAdapter mChatAdapter;
    private String mId;
    private MessageDataSource.MessageListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        findView();
        init();
    }

    private void findView() {
        mEditText = (EditText) findViewById(R.id.text_chat);
        mButtonSend = (ImageView) findViewById(R.id.button_send);
        mRecyclerChat = (RecyclerView) findViewById(R.id.recycler_chat);
        mButtonSend.setOnClickListener(this);
    }

    private void init() {
        String id[] = {"tb", "bt"};
        mMesseageList = new ArrayList<>();
        mMesseageList = FakeContainer.getData();
        mChatAdapter = new ChatAdapter(mMesseageList, this);
        mRecyclerChat.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerChat.addItemDecoration(new LinearItemDecoration(this));
        mRecyclerChat.setAdapter(mChatAdapter);
        mId = id[0] + id[1];
        mListener = MessageDataSource.addMessageListener(mId, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_send:
                String message = mEditText.getText().toString();
                MessageUser messageUser = new MessageUser();
                messageUser.setDate(new Date());
                messageUser.setText(message);
                messageUser.setSender("Ahihi");
                if(message.length()!=0){
                    MessageDataSource.saveMessage(messageUser, mId);
                }
                mEditText.setText("");
                Toast.makeText(this, "size : " + mChatAdapter.getItemCount(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMessageAdded(MessageUser messageUser) {
        mMesseageList.add(messageUser);
        mChatAdapter.notifyItemChanged(mMesseageList.indexOf(messageUser));
        if(messageUser.getText().length()!=0){
            ChatBroadcastReceiver.setData(messageUser.getText(),messageUser.getSender());
            ChatBroadcastReceiver.setupAlarm(getApplicationContext());
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageDataSource.stopListener(mListener);
    }
}
