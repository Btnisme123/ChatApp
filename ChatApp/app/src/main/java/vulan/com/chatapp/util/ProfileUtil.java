package vulan.com.chatapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import vulan.com.chatapp.activity.SignUpActivity;

/**
 * Created by vulan on 18/12/2016.
 */

public class ProfileUtil {
    private static ProfileUtil sProfileUtil;
    private static Context mContext;
    private static ProgressDialog mProgressDialog;

    public static ProfileUtil getInstance(Context context){
        if(sProfileUtil==null){
            synchronized (ProfileUtil.class){
                if(sProfileUtil==null){
                    mContext=context;
                    sProfileUtil=new ProfileUtil();
                    init();
                }
            }
        }
        return  sProfileUtil;
    }

    private static void init() {
        mProgressDialog=new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Authenticating...");

    }
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void updateCurrentUserProfile(String id, final String password, Uri uri) {
        if(!((Activity) mContext).isFinishing()){
            mProgressDialog.show();
        }
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(id)
                .setPhotoUri(uri)
                .build();
        getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "User profile updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext,""+e.getMessage(), Toast.LENGTH_SHORT).show();;
            }
        });
        AuthCredential credential = EmailAuthProvider
                .getCredential(SignUpActivity.sId, SignUpActivity.sPassword);
        getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getCurrentUser().updatePassword(password)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mProgressDialog.dismiss();
                                            Toast.makeText(mContext, "Successful", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext,""+e.getMessage(), Toast.LENGTH_SHORT).show();;
                            }
                        });
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
