package vulan.com.chatapp.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import vulan.com.chatapp.R;
import vulan.com.chatapp.fragment.BaseFragment;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) addFragment();
        onCreateContentView();
        findView();
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getCurrentFragment().onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public void addFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, getFragment()).commit();
    }

    public void replaceFragment(BaseFragment fragment, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.fragment_slide_right_enter, R.animator.fragment_slide_right_exit
        ).replace(R.id.fragmentContainer, fragment, tag)
                .addToBackStack(tag).commit();
    }

    protected abstract BaseFragment getFragment();

    protected abstract void onCreateContentView();

    protected BaseFragment getCurrentFragment() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            return (BaseFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        }
        return null;
    }

    public void popFragment() {
        getFragmentManager().popBackStack();
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
