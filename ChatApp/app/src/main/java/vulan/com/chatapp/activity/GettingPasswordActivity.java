package vulan.com.chatapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import vulan.com.chatapp.R;
import vulan.com.chatapp.util.Constants;

public class GettingPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonReset, mButtonBack;
    private EditText mEditTextEmail;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_password);
        findView();
        initProgressDialog();
    }

    private void findView() {
        mButtonReset = (Button) findViewById(R.id.btn_reset_password);
        mEditTextEmail = (EditText) findViewById(R.id.email_reset);
        mButtonBack = (Button) findViewById(R.id.btn_back);
        mButtonReset.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    private int checkData(String id) {
        if (id.length() < Constants.MINIMUM_LENGTH) {
            return Constants.MINIMUM_LENGTH_STATE;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(id).matches()){
            return Constants.ERROR_EMAIL_STATE;
        }
        return Constants.TRUE_STATE;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_reset_password:
                String email = mEditTextEmail.getText().toString().trim();
                switch (checkData(email)) {
                    case Constants.TRUE_STATE:
                        resetPassword(email);
                        break;
                    case Constants.MINIMUM_LENGTH_STATE:
                        mEditTextEmail.setError(Constants.ERROR_MIN_CHARACTER);
                        break;
                    case Constants.ERROR_EMAIL_STATE:
                        mEditTextEmail.setError(Constants.ERROR_EMAIL);
                        break;
                }
                break;
        }
    }

    public void resetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onResetSuccess();
                        } else {
                            onResetFailed();
                        }
                    }
                });
    }
    public void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progressdialog));
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void onResetSuccess() {
        mProgressDialog.dismiss();
        Toast.makeText(getApplicationContext(), Constants.SEND_EMAIL_SUCCESS, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void onResetFailed() {
        mProgressDialog.dismiss();
        Toast.makeText(getApplicationContext(), Constants.SEND_EMAIL_FAILED, Toast.LENGTH_LONG).show();
    }
}
