package vulan.com.chatapp.activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import vulan.com.chatapp.R;
import vulan.com.chatapp.entity.Contact;
import vulan.com.chatapp.fragment.BaseFragment;
import vulan.com.chatapp.fragment.ContactFragment;
import vulan.com.chatapp.fragment.HomeFragment;

/**
 * Created by VULAN on 9/11/2016.
 */
public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    @Override
    protected BaseFragment getFragment() {
        return new HomeFragment();
    }

    @Override
    protected void onCreateContentView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Contact> list = filter(ContactFragment.sContactList, newText);
        ContactFragment.sContactAdapter.animateTo(list);
        return true;
    }

    private List<Contact> filter(List<Contact> list, String query) {
        query = query.toLowerCase();
        List<Contact> contacts = new ArrayList<>();
        for (Contact item : list) {
            String itemName = item.getName().toLowerCase();
            if (itemName.contains(query)) {
                contacts.add(item);
            }
        }
        return contacts;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
