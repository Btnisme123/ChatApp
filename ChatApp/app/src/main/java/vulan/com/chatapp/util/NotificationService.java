package vulan.com.chatapp.util;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;

import me.leolin.shortcutbadger.ShortcutBadger;
import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.MainActivity;

/**
 * Created by VULAN on 9/26/2016.
 */

public class NotificationService extends IntentService {

    private NotificationManager mNotificationManager;
    private PendingIntent mPendingIntent;
    private static final int NOTIFICATION_ID = 1;

    public NotificationService() {
        super(Constants.NAME_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SettingApplication settingApplication = (SettingApplication) getApplication();
        settingApplication.incrementCount();
        Context context = this.getApplicationContext();
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intentSending = new Intent(this, MainActivity.class);
        mPendingIntent = PendingIntent.getActivity(context, 0, intentSending, PendingIntent.FLAG_UPDATE_CURRENT);
        showNotification();
        ShortcutBadger.applyCount(context, settingApplication.getBadgeCount());
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        Resources resources = getBaseContext().getResources();
        builder.setContentIntent(mPendingIntent)
                .setSmallIcon(R.drawable.ic_chat_bubble)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("123");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
