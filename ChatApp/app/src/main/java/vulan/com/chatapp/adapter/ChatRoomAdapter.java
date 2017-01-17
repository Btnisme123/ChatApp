package vulan.com.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.ChatActivity;
import vulan.com.chatapp.newtype.model.ChatRoom;

/**
 * Created by VULAN on 10/18/2016.
 */

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ItemHolder> {

    private List<ChatRoom> mChatRoomList;
    private Context mContext;

    public ChatRoomAdapter(List<ChatRoom> chatRoomList, Context context) {
        this.mChatRoomList = new ArrayList<>(chatRoomList);
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
        switch (position) {
            case 1:
                holder.mImageAvatar.setImageResource(R.drawable.room1);
                break;
            case 2:
                holder.mImageAvatar.setImageResource(R.drawable.room2);
                break;
            case 3:
                holder.mImageAvatar.setImageResource(R.drawable.room3);
                break;
            case 4:
                holder.mImageAvatar.setImageResource(R.drawable.room4);
                break;
            case 5:
                holder.mImageAvatar.setImageResource(R.drawable.room5);
                break;
        }
    }

    public ChatRoom removeItem(int position) {
        final ChatRoom category = mChatRoomList.remove(position);
        notifyItemRemoved(position);
        return category;
    }

    public void addItem(int position, ChatRoom model) {
        mChatRoomList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ChatRoom category = mChatRoomList.remove(fromPosition);
        mChatRoomList.add(toPosition, category);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void applyAndAnimateRemovals(List<ChatRoom> categoryProducts) {
        int size = mChatRoomList.size();
        for (int i = size - 1; i >= 0; i--) {
            ChatRoom category = mChatRoomList.get(i);
            if (!categoryProducts.contains(category)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAddition(List<ChatRoom> categoryProducts) {
        for (int i = 0, count = categoryProducts.size(); i < count; i++) {
            ChatRoom categoryProduct = categoryProducts.get(i);
            if (!mChatRoomList.contains(categoryProduct)) {
                addItem(i, categoryProduct);
            }
        }
    }

    private void applyAndAnimateMoveItems(List<ChatRoom> categoryProducts) {
        int size = categoryProducts.size();
        for (int toPosition = size - 1; toPosition >= 0; toPosition--) {
            ChatRoom category = categoryProducts.get(toPosition);
            int fromPosition = mChatRoomList.indexOf(category);
            if (fromPosition != 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void animateTo(List<ChatRoom> list) {
        applyAndAnimateAddition(list);
        applyAndAnimateMoveItems(list);
        applyAndAnimateRemovals(list);
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
