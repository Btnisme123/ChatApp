package vulan.com.chatapp.util;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by VULAN on 9/22/2016.
 */

public class SettingApplication extends Application {

    private int mNotificationCount;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

    public void incrementCount() {
        mNotificationCount++;
    }

    public int getBadgeCount() {
        return mNotificationCount;
    }
}
