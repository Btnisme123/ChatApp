package vulan.com.chatapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vulan on 18/12/2016.
 */

public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null) return false;
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnectedOrConnecting();
    }
}
