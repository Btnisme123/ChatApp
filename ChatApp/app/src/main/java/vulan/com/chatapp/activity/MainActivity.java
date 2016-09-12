package vulan.com.chatapp.activity;

import vulan.com.chatapp.fragment.BaseFragment;
import vulan.com.chatapp.fragment.ChatFragment;

/**
 * Created by VULAN on 9/11/2016.
 */
public class MainActivity extends BaseActivity {
    @Override
    protected BaseFragment getFragment() {
        return new ChatFragment();
    }

    @Override
    protected void onCreateContentView() {

    }
}
