package vulan.com.chatapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vulan on 22/11/2016.
 */

public class ChatStarterReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ChatBroadcastReceiver.setupAlarm(context);
    }
}
