package vulan.com.chatapp.util;

import android.app.Application;

import com.firebase.client.Firebase;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by VULAN on 9/22/2016.
 */

public class SettingApplication extends Application {

    private int mNotificationCount;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Firebase.setAndroidContext(this);
    }

    public void incrementCount() {
        mNotificationCount++;
    }

    public int getBadgeCount() {
        return mNotificationCount;
    }
}
