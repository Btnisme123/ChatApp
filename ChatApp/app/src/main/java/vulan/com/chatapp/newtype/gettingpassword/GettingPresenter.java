package vulan.com.chatapp.newtype.gettingpassword;

import android.util.Patterns;

import java.util.regex.Pattern;

import vulan.com.chatapp.util.Constants;

/**
 * Created by vulan on 17/01/2017.
 */

public class GettingPresenter implements GettingContract.Presenter {

    private GettingContract.View mView;

    public GettingPresenter(GettingContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void sendRequest(String email) {
        if (email.length() < Constants.MINIMUM_LENGTH) {
            mView.showLengthError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showFormatError();
        } else
            mView.showResult();
    }
}
