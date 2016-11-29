package vulan.com.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vulan.com.chatapp.R;
import vulan.com.chatapp.entity.DrawerLeftItem;
import vulan.com.chatapp.listener.OnLeftItemClickListener;


/**
 * Created by VULAN on 10/21/2016.
 */

public class RecyclerLeftDrawerAdapter extends RecyclerView.Adapter<RecyclerLeftDrawerAdapter.ItemHolder> {
    private List<DrawerLeftItem> mNavigationDrawerLeftItems;
    private Context mContext;
    private OnLeftItemClickListener mOnRecyclerItemInteractListener;

    public RecyclerLeftDrawerAdapter(Context context, List<DrawerLeftItem> items
    ) {
        mContext = context;
        mNavigationDrawerLeftItems = items;
    }

    public void setOnClick(OnLeftItemClickListener onRecyclerItemInteractListener) {
        mOnRecyclerItemInteractListener = onRecyclerItemInteractListener;
    }

    @Override
    public RecyclerLeftDrawerAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_left_recycler_navigation_drawer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerLeftDrawerAdapter.ItemHolder holder, final int position) {
        DrawerLeftItem item = mNavigationDrawerLeftItems.get(position);
        holder.mImageIcon.setImageResource(item.getImageDrawble());
        holder.mTextTitle.setText(item.getTitle());
        holder.mlinearDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerItemInteractListener.onLeftItemClick(position);
                Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNavigationDrawerLeftItems != null ? mNavigationDrawerLeftItems.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView mImageIcon;
        public LinearLayout mlinearDrawer;
        public TextView mTextTitle;

        public ItemHolder(View itemView) {
            super(itemView);
            mlinearDrawer = (LinearLayout) itemView.findViewById(R.id.linear_navigation_drawer);
            mImageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
        }
    }
}
