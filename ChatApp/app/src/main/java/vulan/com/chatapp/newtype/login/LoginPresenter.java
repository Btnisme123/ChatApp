package vulan.com.chatapp.newtype.login;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vulan.com.chatapp.util.Constants;

/**
 * Created by vulan on 13/01/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    public LoginPresenter(LoginContract.View loginView) {
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }

    @Override
    public void validateCredentials(String username, String password) {
        mLoginView.showProgress();
        if (username.length() < Constants.MINIMUM_LENGTH) {
            mLoginView.showUserNameError();
        } else if (password.length() < Constants.MINIMUM_LENGTH) {
            mLoginView.showPasswordError();
        } else
            mLoginView.login(username,password);

        mLoginView.hideProgress();
    }
}
