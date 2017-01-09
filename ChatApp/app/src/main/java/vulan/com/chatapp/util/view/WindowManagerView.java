package vulan.com.chatapp.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import vulan.com.chatapp.R;

/**
 * Created by vulan on 05/01/2017.
 */

public class WindowManagerView extends RelativeLayout{

    public WindowManagerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_window_manager,null);
    }

    public WindowManagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void findView(){

    }
}
