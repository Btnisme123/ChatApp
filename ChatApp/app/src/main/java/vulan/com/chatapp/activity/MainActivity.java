package vulan.com.chatapp.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vulan.com.chatapp.R;
import vulan.com.chatapp.adapter.RecyclerLeftDrawerAdapter;
import vulan.com.chatapp.newtype.model.ChatRoom;
import vulan.com.chatapp.newtype.model.Contact;
import vulan.com.chatapp.newtype.model.DrawerLeftItem;
import vulan.com.chatapp.fragment.BaseFragment;
import vulan.com.chatapp.fragment.ChatRoomFragment;
import vulan.com.chatapp.fragment.ContactFragment;
import vulan.com.chatapp.fragment.HomeFragment;
import vulan.com.chatapp.listener.OnLeftItemClickListener;
import vulan.com.chatapp.util.Constants;
import vulan.com.chatapp.util.ProfileUtil;
import vulan.com.chatapp.widget.LinearItemDecoration;

import static vulan.com.chatapp.activity.SignUpActivity.sFirebaseAuth;

/**
 * Created by VULAN on 9/11/2016.
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, OnLeftItemClickListener, View.OnClickListener {

    private RecyclerView mLeftRecyclerDrawer;
    private RecyclerLeftDrawerAdapter mRecyclerLeftDrawerAdapter;
    private List<DrawerLeftItem> mDrawerLeftItemList;
    private DrawerLayout mDrawerLayout;
    private ImageView mButtonMenuLeft;
    private SearchView mSearchView;
    private CircleImageView mImageAvatar;
    private TextView mTextFullName;
    private TextView mTextEmail;
    private static final int LOGOUT_POSITION = 1, SETTING_POSITION = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readPasswordFromCache();
        findView();
        init();
        replaceFragment(new HomeFragment(), Constants.HOME_FRAGMENT);
    }

    private void findView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftRecyclerDrawer = (RecyclerView) findViewById(R.id.left_recycler_navigation_drawer);
        mButtonMenuLeft = (ImageView) findViewById(R.id.button_menu_left);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mImageAvatar = (CircleImageView) findViewById(R.id.image_avatar);
        mTextFullName = (TextView) findViewById(R.id.name);
        mTextEmail = (TextView) findViewById(R.id.user_name);
        mSearchView.setOnQueryTextListener(this);
        mButtonMenuLeft.setOnClickListener(this);
    }

    private void init() {
        mDrawerLeftItemList = new ArrayList<>();
        mDrawerLeftItemList.add(new DrawerLeftItem(getString(R.string.change_password), R.drawable.ic_setting));
        mDrawerLeftItemList.add(new DrawerLeftItem(getString(R.string.logout), R.drawable.ic_logout));
        mRecyclerLeftDrawerAdapter = new RecyclerLeftDrawerAdapter(this, mDrawerLeftItemList);
        mLeftRecyclerDrawer.setLayoutManager(new LinearLayoutManager(this));
        mLeftRecyclerDrawer.addItemDecoration(new LinearItemDecoration(this));
        mLeftRecyclerDrawer.setAdapter(mRecyclerLeftDrawerAdapter);
        setInfo();
        mRecyclerLeftDrawerAdapter.setOnClick(this);
    }

    private void setInfo() {
        if (ProfileUtil.getInstance(MainActivity.this).getCurrentUser() != null) {
            mTextFullName.setText(ProfileUtil.getInstance(MainActivity.this).getCurrentUser().getDisplayName());
            mTextEmail.setText(ProfileUtil.getInstance(MainActivity.this).getCurrentUser().getEmail());
            if (ProfileUtil.getInstance(MainActivity.this).getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this).load(ProfileUtil.getInstance(MainActivity.this).getCurrentUser().getPhotoUrl()).into(mImageAvatar);
            } else {
                Log.e("", "null");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfo();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (HomeFragment.sViewPager != null) {
            switch (HomeFragment.sViewPager.getCurrentItem()) {
                case 0:
                    List<ChatRoom> listChatRoom = filterChatRoom(ChatRoomFragment.sChatRoomList, newText);
                    ChatRoomFragment.sChatRoomAdapter.animateTo(listChatRoom);
                    break;
                case 1:
                    List<Contact> listContact = filterContact(ContactFragment.sContactList, newText);
                    ContactFragment.sContactAdapter.animateTo(listContact);
                    break;
            }
        }

        return true;
    }

    private List<Contact> filterContact(List<Contact> list, String query) {
        query = query.toLowerCase();
        List<Contact> contacts = new ArrayList<>();
        for (Contact item : list) {
            String itemName = item.getName().toLowerCase();
            if (itemName.contains(query)) {
                contacts.add(item);
            }
        }
        return contacts;
    }

    private List<ChatRoom> filterChatRoom(List<ChatRoom> list, String query) {
        query = query.toLowerCase();
        List<ChatRoom> contacts = new ArrayList<>();
        for (ChatRoom item : list) {
            String itemName = item.getTitle().toLowerCase();
            if (itemName.contains(query)) {
                contacts.add(item);
            }
        }
        return contacts;
    }

    public void replaceFragment(BaseFragment fragment, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.fragment_slide_right_enter, R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_left_enter, R.animator.fragment_slide_right_exit)
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack("").commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public void onLeftItemClick(int position) {
        switch (position) {
            case LOGOUT_POSITION:
                sFirebaseAuth.signOut();
                removeCache();
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                break;
            case SETTING_POSITION:
                startActivity(new Intent(MainActivity.this, ChangingPasswordActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_menu_left:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;

        }
    }

    private void readPasswordFromCache() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String id = sharedPreferences.getString(Constants.USER_ID, Constants.DEFAULT_VALUE);
        String password = sharedPreferences.getString(Constants.USER_PASSWORD, Constants.DEFAULT_VALUE);
        if (!id.equals(Constants.DEFAULT_VALUE)) {
            SignUpActivity.sId = id;
            SignUpActivity.sPassword = password;
        } else if (SignUpActivity.sId != null) {

        } else {
            switchToLoginActivity();
        }
    }

    private void switchToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void removeCache() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USER_ID, Constants.DEFAULT_VALUE);
        editor.putString(Constants.USER_PASSWORD, Constants.DEFAULT_VALUE);
        editor.apply();
    }
}
