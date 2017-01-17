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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.ChatActivity;
import vulan.com.chatapp.newtype.model.Contact;
import vulan.com.chatapp.util.FakeContainer;

/**
 * Created by VULAN on 10/18/2016.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ItemHolder> {

    private List<Contact> mContactList;
    private Context mContext;

    public ContactAdapter(List<Contact> mContactList, Context context) {
        this.mContactList = new ArrayList<>(mContactList);
        ;
        this.mContext = context;
    }

    @Override
    public ContactAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ItemHolder holder, int position) {
        holder.bind(position);
    }

    public Contact removeItem(int position) {
        final Contact category = mContactList.remove(position);
        notifyItemRemoved(position);
        return category;
    }

    public void addItem(int position, Contact model) {
        mContactList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Contact category = mContactList.remove(fromPosition);
        mContactList.add(toPosition, category);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addAll(List<Contact> list) {
        mContactList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearList() {
        mContactList.clear();
        notifyDataSetChanged();
    }

    private void applyAndAnimateRemovals(List<Contact> categoryProducts) {
        int size = mContactList.size();
        for (int i = size - 1; i >= 0; i--) {
            Contact category = mContactList.get(i);
            if (!categoryProducts.contains(category)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAddition(List<Contact> categoryProducts) {
        for (int i = 0, count = categoryProducts.size(); i < count; i++) {
            Contact categoryProduct = categoryProducts.get(i);
            if (!mContactList.contains(categoryProduct)) {
                addItem(i, categoryProduct);
            }
        }
    }

    private void applyAndAnimateMoveItems(List<Contact> categoryProducts) {
        int size = categoryProducts.size();
        for (int toPosition = size - 1; toPosition >= 0; toPosition--) {
            Contact category = categoryProducts.get(toPosition);
            int fromPosition = mContactList.indexOf(category);
            if (fromPosition != 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void animateTo(List<Contact> list) {
        applyAndAnimateAddition(list);
        applyAndAnimateMoveItems(list);
        applyAndAnimateRemovals(list);
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.image_avatar)
        CircleImageView mImageAvatar;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        private void bind(int position){
            Contact contact=mContactList.get(position);
           mTextTitle.setText(contact.getName());
            mImageAvatar.setImageResource(contact.getmImageFake());
        }

        @OnClick(R.id.layout_chat_room)
        void onClick(){
            mContext.startActivity(new Intent(mContext, ChatActivity.class));
        }

    }
}