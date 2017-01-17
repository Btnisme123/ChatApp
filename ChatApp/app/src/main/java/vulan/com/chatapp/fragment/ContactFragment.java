package vulan.com.chatapp.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vulan.com.chatapp.R;
import vulan.com.chatapp.adapter.ContactAdapter;
import vulan.com.chatapp.newtype.model.Contact;
import vulan.com.chatapp.util.FakeContainer;
import vulan.com.chatapp.widget.LinearItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment{

    private RecyclerView mListRoomRecyclerView;
    public static ContactAdapter sContactAdapter;
    public static List<Contact> sContactList;

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
        sContactList = new ArrayList<>();
        sContactList = FakeContainer.getDataContact();
        sContactAdapter = new ContactAdapter(sContactList, getActivity());
        mListRoomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListRoomRecyclerView.addItemDecoration(new LinearItemDecoration(getActivity()));
        mListRoomRecyclerView.setAdapter(sContactAdapter);
    }
}
