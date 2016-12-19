package vulan.com.chatapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.SignUpActivity;
import vulan.com.chatapp.entity.MessageUser;

/**
 * Created by VULAN on 9/18/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemHolder> {

    private List<MessageUser> mMessageUserList;
    private Context mContext;
    private ProgressDialog mProgressDialog;

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
        final MessageUser messageUser = mMessageUserList.get(position);
        holder.mTextTime.setText(messageUser.getDateString());
        if (messageUser.getText().contains("firebasestorage.googleapis.com")) {
            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mTxtEmojicon.setVisibility(View.GONE);
            holder.mProgressBar.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(messageUser.getText())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.mProgressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.mProgressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.mImageView);
        } else {
            holder.mTxtEmojicon.setVisibility(View.VISIBLE);
            holder.mImageView.setVisibility(View.GONE);
            holder.mTxtEmojicon.setText(messageUser.getText());
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
        }
        if (SignUpActivity.sId != null && messageUser.getSender() != null) {
            if (messageUser.getSender().equals(SignUpActivity.sId)) {
                holder.mImageAvatar.setVisibility(View.VISIBLE);
                holder.mLayoutItemChat.setGravity(Gravity.RIGHT);
                holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.mTxtEmojicon.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                holder.mImageAvatar.setVisibility(View.GONE);
                holder.mLayoutItemChat.setGravity(Gravity.LEFT);
                holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
                holder.mTxtEmojicon.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }
        holder.mImageAvatar.setVisibility(View.VISIBLE);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("sender ", "" + messageUser.getSender());
                Log.e("id ", "" + SignUpActivity.sId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessageUserList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        EmojiconTextView mTxtEmojicon;
        CardView mCardView;
        TextView mTextTime;
        CircleImageView mImageAvatar;
        RelativeLayout mLayoutItemChat;
        ImageView mImageView;
        ProgressBar mProgressBar;

        public ItemHolder(View itemView) {
            super(itemView);
            mProgressBar= (ProgressBar) itemView.findViewById(R.id.progressBar);
            mImageView = (ImageView) itemView.findViewById(R.id.image_server);
            mTxtEmojicon = (EmojiconTextView) itemView.findViewById(R.id.txtEmojicon);
            mTextTime = (TextView) itemView.findViewById(R.id.text_time);
            mCardView = (CardView) itemView.findViewById(R.id.card_item_chat);
            mImageAvatar = (CircleImageView) itemView.findViewById(R.id.image_avatar);
            mLayoutItemChat = (RelativeLayout) itemView.findViewById(R.id.layout_item_chat);
        }
    }
}
