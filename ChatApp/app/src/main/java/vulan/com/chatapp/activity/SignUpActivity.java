package vulan.com.chatapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vulan.com.chatapp.R;
import vulan.com.chatapp.util.Constants;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int MINIMUM_LENGTH = 6;
    private TextView mTextView;
    private Button mButtonSignUp;
    private Button mButtonSignIn;
    private EditText mTextPassword;
    private EditText mTextID;
    private TextView mButtonResetPassword;

    public static FirebaseAuth sFirebaseAuth = FirebaseAuth.getInstance();
    public static String sId, sPassword;
    private ProgressDialog mProgressDialog;
    private CheckBox mCheckboxPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findView();
        init();
    }

    private void findView() {
        mTextView = (TextView) findViewById(R.id.text_logo);
        mButtonSignUp = (Button) findViewById(R.id.sign_up_button);
        mButtonSignIn = (Button) findViewById(R.id.sign_in_button);
        mTextID = (EditText) findViewById(R.id.edt_email);
        mTextPassword = (EditText) findViewById(R.id.edt_password);
        mCheckboxPassword = (CheckBox) findViewById(R.id.checkbox);
        mButtonResetPassword = (TextView) findViewById(R.id.button_reset);
        mButtonSignUp.setOnClickListener(this);
        mButtonSignIn.setOnClickListener(this);
        mCheckboxPassword.setOnCheckedChangeListener(this);
        mButtonResetPassword.setOnClickListener(this);
    }

    private void init() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Authenticating...");
        Typeface typeface = Typeface.createFromAsset(getAssets(), Constants.FONT);
        mTextView.setTypeface(typeface);
        readPasswordFromCache();
    }

    private void readPasswordFromCache() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String id = sharedPreferences.getString(Constants.USER_ID, Constants.DEFAULT_VALUE);
        String password = sharedPreferences.getString(Constants.USER_PASSWORD, Constants.DEFAULT_VALUE);
        if (!id.equals(Constants.DEFAULT_VALUE)) {
            signIn(id, password);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        String id = mTextID.getText().toString().trim();
        String password = mTextPassword.getText().toString().trim();
        if (v.getId() == R.id.button_reset) {
            startActivity(new Intent(SignUpActivity.this, GettingPasswordActivity.class));
        } else {
            switch (checkData(id, password)) {
                case Constants.TRUE_STATE:
                    switch (v.getId()) {
                        case R.id.sign_up_button:
                            signUp(id, password);
                            break;
                        case R.id.sign_in_button:
                            signIn(id, password);
                            break;
                    }
                    break;
                case Constants.MINIMUM_LENGTH_STATE:
                    if (mTextID.getText().toString().length() < 6) {
                        mTextID.setError(Constants.ERROR_MIN_CHARACTER);
                    }
                    if (mTextPassword.getText().toString().length() < 6) {
                        mTextPassword.setError(Constants.ERROR_MIN_CHARACTER);
                    }
                case Constants.ERROR_EMAIL_STATE:
                    mTextID.setError(Constants.ERROR_EMAIL);
                    break;
            }
        }
    }

    private void signUp(String id, final String password) {
        mProgressDialog.show();
        sFirebaseAuth = FirebaseAuth.getInstance();
        sFirebaseAuth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressDialog.dismiss();
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    try {

                    } catch (Exception e) {
                        Toast.makeText(SignUpActivity.this, "The email address is already in use by another account ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private int checkData(String id, String password) {
        if (id.length() < MINIMUM_LENGTH || password.length() < MINIMUM_LENGTH) {
            return Constants.MINIMUM_LENGTH_STATE;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            return Constants.ERROR_EMAIL_STATE;
        }
        return Constants.TRUE_STATE;
    }

    private void signIn(final String id, final String password) {

        mProgressDialog.show();
        sFirebaseAuth.signInWithEmailAndPassword(id, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressDialog.dismiss();
                try {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "" + task.getResult(), Toast.LENGTH_SHORT).show();
                    } else {
                        sId = id;
                        sPassword = password;
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(SignUpActivity.this, "Error:  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (checkData(mTextID.getText().toString(), mTextPassword.getText().toString()) == Constants.TRUE_STATE) {
            if (b) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.USER_ID, mTextID.getText().toString());
                editor.putString(Constants.USER_PASSWORD, mTextPassword.getText().toString());
                editor.apply();
            }
        }
    }
}
