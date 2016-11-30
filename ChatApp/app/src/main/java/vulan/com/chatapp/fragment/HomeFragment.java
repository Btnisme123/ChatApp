package vulan.com.chatapp.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import vulan.com.chatapp.R;
import vulan.com.chatapp.adapter.TabFragmentAdapter;

public class HomeFragment extends BaseFragment {

    public static ViewPager sViewPager;
    private TabLayout mTabLayout;
    private TabFragmentAdapter mTabFragmentAdapter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateContentView(View rootView) {
        findView(rootView);
        init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    private void findView(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        sViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
    }

    private void init() {
        mTabFragmentAdapter = new TabFragmentAdapter(getChildFragmentManager());
        mTabFragmentAdapter.addFragment(new ChatRoomFragment(), getString(R.string.room));
        mTabFragmentAdapter.addFragment(new ContactFragment(), getString(R.string.contact));
        sViewPager.setAdapter(mTabFragmentAdapter);
        mTabLayout.setupWithViewPager(sViewPager);
        mTabLayout.setTabsFromPagerAdapter(mTabFragmentAdapter);
        mTabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_layout_background_color));
        mTabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.white),
                (ContextCompat.getColor(getActivity(), R.color.white)));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mTabLayout.setSelectedTabIndicatorHeight(getResources().getDimensionPixelSize(R.dimen.common_size_3));
    }
}
