package vulan.com.chatapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vulan.com.chatapp.R;
import vulan.com.chatapp.entity.MessageUser;

/**
 * Created by VULAN on 9/18/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemHolder> {

    private List<MessageUser> mMessageUserList;

    public ChatAdapter(List<MessageUser> messageUserList) {
        this.mMessageUserList = messageUserList;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        MessageUser messageUser = mMessageUserList.get(position);
        holder.mTextChat.setText(messageUser.getText());
        if (messageUser.getSender().equals("")) {
            holder.mTextChat.setGravity(Gravity.LEFT);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageUserList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView mTextChat;

        public ItemHolder(View itemView) {
            super(itemView);
            mTextChat = (TextView) itemView.findViewById(R.id.text_chat);
        }
    }
}
