package vulan.com.chatapp.util;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by vulan on 18/12/2016.
 */

public class ProfileUtil {
    private static ProfileUtil sProfileUtil;
    private  FirebaseAuth mAuthStateListener;

    public static ProfileUtil getInstance(){
        if(sProfileUtil==null){
            synchronized (ProfileUtil.class){
                if(sProfileUtil==null){
                    sProfileUtil=new ProfileUtil();
                }
            }
        }
        return  sProfileUtil;
    }

    
}
