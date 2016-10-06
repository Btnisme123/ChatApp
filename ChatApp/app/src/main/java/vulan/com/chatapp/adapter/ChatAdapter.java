package vulan.com.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import vulan.com.chatapp.R;
import vulan.com.chatapp.entity.MessageUser;
import vulan.com.chatapp.util.Constants;

/**
 * Created by VULAN on 9/18/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemHolder> {

    private List<MessageUser> mMessageUserList;
    private Context mContext;

    public ChatAdapter(List<MessageUser> messageUserList, Context context) {
        this.mMessageUserList = messageUserList;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        MessageUser messageUser = mMessageUserList.get(position);
        holder.mTextTime.setText(messageUser.getDateString());
        holder.mTextChat.setText(messageUser.getText());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mTextTime.getVisibility() == View.VISIBLE) {
                    holder.mTextTime.setVisibility(View.GONE);
                } else {
                    holder.mTextTime.setVisibility(View.VISIBLE);
                }
            }
        });
        if (messageUser.getSender().equals("ba")) {
            holder.mLayoutItemChat.setGravity(Gravity.RIGHT);
            holder.mImageAvatar.setVisibility(View.GONE);
            holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.mTextChat.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.mLayoutItemChat.setGravity(Gravity.LEFT);
            holder.mImageAvatar.setVisibility(View.VISIBLE);
            holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.mTextChat.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return mMessageUserList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView mTextChat;
        CardView mCardView;
        TextView mTextTime;
        CircleImageView mImageAvatar;
        LinearLayout mLayoutItemChat;

        public ItemHolder(View itemView) {
            super(itemView);
            mTextChat = (TextView) itemView.findViewById(R.id.text_chat);
            mTextTime = (TextView) itemView.findViewById(R.id.text_time);
            mCardView = (CardView) itemView.findViewById(R.id.card_item_chat);
            mImageAvatar = (CircleImageView) itemView.findViewById(R.id.image_avatar);
            mLayoutItemChat = (LinearLayout) itemView.findViewById(R.id.layout_item_chat);
        }
    }
}
