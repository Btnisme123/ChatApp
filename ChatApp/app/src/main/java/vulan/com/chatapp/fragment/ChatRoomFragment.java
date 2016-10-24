package vulan.com.chatapp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vulan.com.chatapp.R;
import vulan.com.chatapp.adapter.ChatRoomAdapter;
import vulan.com.chatapp.entity.ChatRoom;
import vulan.com.chatapp.util.FakeContainer;
import vulan.com.chatapp.widget.LinearItemDecoration;

/**
 * Created by VULAN on 10/18/2016.
 */

public class ChatRoomFragment extends BaseFragment {
    private RecyclerView mListRoomRecyclerView;
    private ChatRoomAdapter mChatRoomAdapter;
    private List<ChatRoom> mChatRoomList;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_chat_room;
    }

    @Override
    protected void onCreateContentView(View rootView) {
        findView(rootView);
        init();
    }

    private void findView(View rootView) {
        mListRoomRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_chat_room);
    }

    private void init() {
        mChatRoomList = new ArrayList<>();
        mChatRoomList = FakeContainer.getDataRoom();
        mChatRoomAdapter = new ChatRoomAdapter(mChatRoomList,getActivity());
        mListRoomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListRoomRecyclerView.addItemDecoration(new LinearItemDecoration(getActivity()));
        mListRoomRecyclerView.setAdapter(mChatRoomAdapter);
    }
}
