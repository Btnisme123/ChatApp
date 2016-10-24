package vulan.com.chatapp.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vulan.com.chatapp.R;


public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public LinearItemDecoration(Context context) {
        this(context.getResources().getDimensionPixelSize(R.dimen.common_size_15));
    }

    public LinearItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position == 0 && outRect.top == 0) {
            outRect.top = mSpace;
        }
        outRect.bottom = mSpace;
        outRect.right = mSpace;
        outRect.left = mSpace;
    }
}
