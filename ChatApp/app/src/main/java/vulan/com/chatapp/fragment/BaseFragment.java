package vulan.com.chatapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayoutId(), container, false);
        onCreateContentView(rootView);
        return rootView;
    }

    public void onBackPressed() {
        getBaseActivity().popFragment();
    }

    private String getTitle() {
        return getString(R.string.app_name);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected boolean enableBackButton() {
        return true;
    }

    protected abstract int getFragmentLayoutId();

    protected abstract void onCreateContentView(View rootView);
}
