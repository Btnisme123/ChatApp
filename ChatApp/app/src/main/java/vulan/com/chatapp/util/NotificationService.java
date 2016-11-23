package vulan.com.chatapp.util;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import me.leolin.shortcutbadger.ShortcutBadger;
import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.ChatActivity;
import vulan.com.chatapp.activity.MainActivity;

/**
 * Created by VULAN on 9/26/2016.
 */

public class NotificationService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START="action_start";
    private static final String ACTION_DELETE="action_delete";

    public NotificationService() {
        super(Constants.NAME_SERVICE);
    }

    public static Intent createIntentStartNotificationService(Context context,String id , String content){
        Intent intent=new Intent(context,NotificationService.class);
        Bundle bundle=new Bundle();
        bundle.putString(Constants.USER_ID,id);
        bundle.putString(Constants.USER_CONTENT,content);
        intent.putExtras(bundle);
        intent.setAction(ACTION_START);
        return intent;
    }


    public static Intent deleteIntentStartNotificationService(Context context){
        Intent intent=new Intent(context,NotificationService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
      try{
          String action=intent.getAction();
          if(action.equals(ACTION_START)){
              showNotification(intent.getExtras().getString(Constants.USER_ID),intent.getExtras().getString(Constants.USER_CONTENT));
          }
      }finally {
          WakefulBroadcastReceiver.completeWakefulIntent(intent);
      }
    }

    public void showNotification(String id , String content) {
        Notification.Builder builder=new Notification.Builder(this);
        builder.setContentTitle("Test notification")
                .setAutoCancel(true)
                .setContentText(content)
                .setContentTitle(id)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
        Intent intent=new Intent(this, ChatActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,NOTIFICATION_ID, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(ChatBroadcastReceiver.getDeletingIntent(this));
    NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}
