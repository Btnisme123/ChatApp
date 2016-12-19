package vulan.com.chatapp.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import vulan.com.chatapp.R;
import vulan.com.chatapp.adapter.ChatAdapter;
import vulan.com.chatapp.entity.ChatRoom;
import vulan.com.chatapp.entity.MessageUser;
import vulan.com.chatapp.util.ChatBroadcastReceiver;
import vulan.com.chatapp.util.Constants;
import vulan.com.chatapp.util.FakeContainer;
import vulan.com.chatapp.util.MessageDataSource;
import vulan.com.chatapp.widget.LinearItemDecoration;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, MessageDataSource.MessageCallback, EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    private List<MessageUser> mMesseageList;
    private RecyclerView mRecyclerChat;
    private EmojiconEditText mEditEmojicon;
    private ImageView mButtonSend,mButtonCamera,mButtonGallery;
    private ChatAdapter mChatAdapter;
    private String mId;
    private MessageDataSource.MessageListener mListener;
    private ImageView mTextEmoji;
    private FrameLayout mFrameEmoji;
    private static final int WEIGHT_7=7,WEIGHT_10=10;
    private StorageReference mStorageReference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        findView();
        init();
        setEmojiconFragment(false);
    }

    private void findView() {
        mEditEmojicon = (EmojiconEditText) findViewById(R.id.text_chat);
        mButtonSend = (ImageView) findViewById(R.id.button_send);
        mRecyclerChat = (RecyclerView) findViewById(R.id.recycler_chat);
        mTextEmoji= (ImageView) findViewById(R.id.image_emoji);
        mFrameEmoji= (FrameLayout) findViewById(R.id.emojicons);
        mButtonCamera= (ImageView) findViewById(R.id.image_camera);
        mButtonGallery= (ImageView) findViewById(R.id.image_gallery);
        mButtonSend.setOnClickListener(this);
        mTextEmoji.setOnClickListener(this);
        mButtonCamera.setOnClickListener(this);
        mFrameEmoji.setVisibility(View.GONE);
        setParam(WEIGHT_10);
    }

    private void init() {
        mStorageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://chatapp-a87a2.appspot.com");
        String id[] = {"tb", "bt"};
        mMesseageList = new ArrayList<>();
        mMesseageList = FakeContainer.getData();
        mChatAdapter = new ChatAdapter(mMesseageList, this);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerChat.setLayoutManager(linearLayoutManager);
        mRecyclerChat.addItemDecoration(new LinearItemDecoration(this));
        mRecyclerChat.setAdapter(mChatAdapter);
        mId = id[0] + id[1];
        mListener = MessageDataSource.addMessageListener(mId, this);
        mChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                int count=mChatAdapter.getItemCount();
                int lastVisiblePosition=linearLayoutManager.findLastVisibleItemPosition();
                if(lastVisiblePosition==-1||positionStart>=(count-1)&&lastVisiblePosition==(positionStart-1)){
                    mRecyclerChat.scrollToPosition(positionStart);
                }
            }
        });

    }

    private void setParam(int weight){
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,weight);
        mRecyclerChat.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_send:
                String message = mEditEmojicon.getText().toString();
                MessageUser messageUser = new MessageUser();
                messageUser.setDate(new Date());
                messageUser.setText(message);
                messageUser.setSender("Ahihi");
                if(message.length()!=0){
                    MessageDataSource.saveMessage(messageUser, mId);
                }
                mEditEmojicon.setText("");
                break;
            case R.id.image_emoji:
                if(mFrameEmoji.getVisibility()==View.GONE){
                    mTextEmoji.setImageResource(R.drawable.ic_happiness);
                    setParam(WEIGHT_7);
                    mFrameEmoji.setVisibility(View.VISIBLE);
                }else{
                    mTextEmoji.setImageResource(R.drawable.ic_emoji);
                    setParam(WEIGHT_10);
                    mFrameEmoji.setVisibility(View.GONE);
                }
                break;
            case R.id.image_camera:
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Constants.CAMERA_CODE);
                break;
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

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Constants.CAMERA_CODE&&resultCode==RESULT_OK) {
            final Uri uri = data.getData();
            final StorageReference filePath = mStorageReference.child("Photos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChatActivity.this, "Failure", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.e("", "" + uri.toString());
                            MessageUser messageUser = new MessageUser();
                            messageUser.setDate(new Date());
                            messageUser.setText(uri.toString());
                            MessageDataSource.saveMessage(messageUser, mId);
                        }
                    });

                }
            });
        }
    }
}
