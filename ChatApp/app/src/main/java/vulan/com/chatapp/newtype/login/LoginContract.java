package vulan.com.chatapp.newtype.login;

import vulan.com.chatapp.newtype.BaseView;

/**
 * Created by vulan on 13/01/2017.
 */

public class LoginContract {
    interface View extends BaseView {
        void showProgress();

        void hideProgress();

        void showUserNameError();

        void showPasswordError();

        void login(String username, String password);
    }

    interface Presenter {
        void validateCredentials(String username, String password);
    }
}
