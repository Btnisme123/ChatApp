package vulan.com.chatapp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by vulan on 22/11/2016.
 */

public class ChatBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION_SERVICE = "ACTION_DELETE_NOTIFICATION_SERVICE";
    private static final int NOTIFICATION_INTERVAL_MINUTES = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;
        if (action.equals(ACTION_START_NOTIFICATION_SERVICE)) {
            serviceIntent = NotificationService.createIntentStartNotificationService(context,
                    intent.getExtras().getString(Constants.USER_ID),
                    intent.getExtras().getString(Constants.USER_CONTENT));
        } else if (action.equals(ACTION_DELETE_NOTIFICATION_SERVICE)) {
            serviceIntent = NotificationService.deleteIntentStartNotificationService(context);
        }
        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }

    private static long triggerAt(Date dateNow) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        return calendar.getTimeInMillis();
    }

    public static void setupAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getStartingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                triggerAt(new Date()),
                NOTIFICATION_INTERVAL_MINUTES * AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                pendingIntent);
    }

    public static PendingIntent getStartingIntent(Context context) {
        Intent intent = new Intent(context, ChatBroadcastReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeletingIntent(Context context) {
        Intent intent = new Intent(context, ChatBroadcastReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
