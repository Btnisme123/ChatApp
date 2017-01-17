package vulan.com.chatapp.newtype.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vulan.com.chatapp.R;
import vulan.com.chatapp.activity.GettingPasswordActivity;
import vulan.com.chatapp.activity.MainActivity;
import vulan.com.chatapp.util.Constants;

import static com.google.common.base.Preconditions.checkNotNull;
import static vulan.com.chatapp.activity.SignUpActivity.sFirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    LoginPresenter mLoginPresenter;
    ProgressDialog mProgressDialog;

    @BindView(R.id.text_logo)
    TextView mTextView;

    @BindView(R.id.sign_up_button)
    TextView mButtonSignUp;

    @BindView(R.id.sign_in_button)
    TextView mButtonSignIn;

    @BindView(R.id.edt_email)
    TextView mTextID;

    @BindView(R.id.edt_password)
    TextView mTextPassword;

    @BindView(R.id.checkbox)
    TextView mCheckboxPassword;

    @BindView(R.id.button_reset)
    TextView mButtonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenter(this);
        ButterKnife.bind(this);
        initProgressDialog();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Authenticating...");
    }

    @Override
    public void showProgress() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showUserNameError() {
        mTextID.setError(Constants.ERROR_MIN_CHARACTER);
    }

    @Override
    public void showPasswordError() {
        mTextPassword.setError(Constants.ERROR_MIN_CHARACTER);
    }

    @Override
    public void login(String username, String password) {
        sFirebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick({R.id.sign_in_button,R.id.button_reset})
    void onClickButton(View view) {
         if(view.getId()==R.id.sign_in_button){
             mLoginPresenter.validateCredentials(mTextID.getText().toString(), mTextPassword.getText().toString());
         }else{
             startActivity(new Intent(this, GettingPasswordActivity.class));
         }
    }

    @Override
    public void setPresenter(Object presenter) {
        //checkNotNull(presenter);
    }
}
