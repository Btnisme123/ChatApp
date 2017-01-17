package vulan.com.chatapp.newtype.gettingpassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vulan.com.chatapp.R;
import vulan.com.chatapp.newtype.login.LoginActivity;
import vulan.com.chatapp.util.Constants;

public class GettingPasswordActivity extends Activity implements GettingContract.View{

    @BindView(R.id.email_reset)
    EditText mEditTextEmail;

    GettingPresenter mGettingPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mGettingPresenter=new GettingPresenter(this);
    }

    @OnClick({R.id.btn_reset_password,R.id.btn_back})
     void onClick(View view){
        if(view.getId()==R.id.btn_reset_password){
            mGettingPresenter.sendRequest(mEditTextEmail.getText().toString());
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void showLengthError() {
        mEditTextEmail.setError(Constants.ERROR_MIN_CHARACTER);
    }

    @Override
    public void showFormatError() {
        mEditTextEmail.setError(Constants.ERROR_EMAIL);
    }

    @Override
    public void showResult() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
