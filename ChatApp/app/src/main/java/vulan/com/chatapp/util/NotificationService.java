package vulan.com.chatapp.util;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.ChatActivity;

/**
 * Created by VULAN on 9/26/2016.
 */

public class NotificationService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "action_start";
    private static final String ACTION_DELETE = "action_delete";

    public NotificationService() {
        super(Constants.NAME_SERVICE);
    }

    public static Intent createIntentStartNotificationService(Context context, String id, String content) {
        Intent intent = new Intent(context, NotificationService.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_ID, id);
        bundle.putString(Constants.USER_CONTENT, content);
        intent.putExtras(bundle);
        intent.setAction(ACTION_START);
        return intent;
    }


    public static Intent deleteIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String action = intent.getAction();
            String userId = intent.getExtras().getString(Constants.USER_ID);
            String userContent = intent.getExtras().getString(Constants.USER_CONTENT);
            if (action.equals(ACTION_START) && userContent!=null
                    && isIDfromcached()) {
                if(userContent.length()>0){
                    showNotification(userId, userContent);
                }
                }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    public void showNotification(String id, String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Test notification")
                .setAutoCancel(true)
                .setContentText(content)
                .setContentTitle(id)
                .setSmallIcon(R.drawable.cuong);
        Intent intent = new Intent(this, ChatActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(ChatBroadcastReceiver.getDeletingIntent(this));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private boolean isIDfromcached() {
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        Log.e("1232323",""+sharedPreference.getString(Constants.USER_ID, Constants.DEFAULT_VALUE));
        return sharedPreference.getString(Constants.USER_ID, Constants.DEFAULT_VALUE).equals(Constants.DEFAULT_VALUE);
    }
}
