package vulan.com.chatapp.util;

import android.content.Context;
import android.content.Intent;

import vulan.com.chatapp.service.NotificationService;

/**
 * Created by VULAN on 9/26/2016.
 */

public class NotificationUtil {

    public static void updateChatInformation(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        //PendingIntent pendingIntent=new PendingIntent.getService(context,)
    }
}
