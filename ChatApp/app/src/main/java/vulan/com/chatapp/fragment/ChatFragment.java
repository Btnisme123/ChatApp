package vulan.com.chatapp.fragment;


import android.os.Bundle;
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
import vulan.com.chatapp.util.FakeContainer;
import vulan.com.chatapp.util.MessageDataSource;
import vulan.com.chatapp.widget.LinearItemDecoration;

public class ChatFragment extends BaseFragment implements View.OnClickListener, MessageDataSource.MessageCallback {

    private List<MessageUser> mMesseageList;
    private RecyclerView mRecyclerChat;
    private EditText mEditText;
    private ImageView mButtonSend;
    private ChatAdapter mChatAdapter;
    private String mId;
    private MessageDataSource.MessageListener mListener;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void onCreateContentView(View rootView) {
        findView(rootView);
        init();
    }

    private void findView(View view) {
        mEditText = (EditText) view.findViewById(R.id.text_chat);
        mButtonSend = (ImageView) view.findViewById(R.id.button_send);
        mRecyclerChat = (RecyclerView) view.findViewById(R.id.recycler_chat);
        mButtonSend.setOnClickListener(this);
    }

    private void init() {
        String id[] = {"tb", "bt"};
        mMesseageList = new ArrayList<>();
        mMesseageList = FakeContainer.getData();
        mChatAdapter = new ChatAdapter(mMesseageList, getActivity());
        mRecyclerChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerChat.addItemDecoration(new LinearItemDecoration(getActivity()));
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
                MessageDataSource.saveMessage(messageUser, mId);
                mEditText.setText("");
                Toast.makeText(getActivity(), "size : " + mChatAdapter.getItemCount(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMessageAdded(MessageUser messageUser) {
        mMesseageList.add(messageUser);
        mChatAdapter.notifyItemChanged(mMesseageList.indexOf(messageUser));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageDataSource.stopListener(mListener);
    }
}
