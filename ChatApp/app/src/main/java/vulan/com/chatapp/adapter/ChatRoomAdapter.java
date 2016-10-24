package vulan.com.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.ChatActivity;
import vulan.com.chatapp.entity.ChatRoom;

/**
 * Created by VULAN on 10/18/2016.
 */

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ItemHolder> {

    private List<ChatRoom> mChatRoomList;
    private Context mContext;

    public ChatRoomAdapter(List<ChatRoom> chatRoomList, Context context) {
        this.mChatRoomList = chatRoomList;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_chat, parent, false);
        return new ChatRoomAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ChatRoom chatRoom = mChatRoomList.get(position);
        holder.mTextTitle.setText(chatRoom.getTitle());
        holder.mTextContent.setText(chatRoom.getContent());
        holder.mChatRoomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ChatActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatRoomList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView mTextTitle;
        TextView mTextContent;
        CircleImageView mImageAvatar;
        LinearLayout mChatRoomLayout;

        public ItemHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextContent = (TextView) itemView.findViewById(R.id.text_content);
            mImageAvatar = (CircleImageView) itemView.findViewById(R.id.image_avatar);
            mChatRoomLayout = (LinearLayout) itemView.findViewById(R.id.layout_chat_room);
        }
    }
}
