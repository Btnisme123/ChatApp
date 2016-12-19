package vulan.com.chatapp.util;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void updateCurrentUserProfile(String displayName, String avatarUrl) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(Uri.parse(avatarUrl))
                .build();
        getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("U_STATUS", "User profile updated.");
                        }
                    }
                });
    }

    public void reauthFirebase(String email, String password) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);
        getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("DISPLAY_NAME", getCurrentUser().getDisplayName());
                    }
                });
    }
}
