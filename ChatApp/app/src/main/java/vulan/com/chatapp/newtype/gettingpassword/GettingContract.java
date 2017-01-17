package vulan.com.chatapp.newtype.gettingpassword;

import vulan.com.chatapp.newtype.BaseView;

/**
 * Created by vulan on 17/01/2017.
 */

public class GettingContract {
    interface View extends BaseView {
        void showLengthError();

        void showFormatError();

        void showResult();
    }

    interface Presenter {
        void sendRequest(String email);
    }
}
