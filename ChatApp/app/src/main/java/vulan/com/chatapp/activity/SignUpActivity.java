package vulan.com.chatapp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vulan.com.chatapp.R;
import vulan.com.chatapp.util.Constants;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MINIMUM_LENGTH = 6;
    private TextView mTextView;
    private Button mButtonSignUp;
    private Button mButtonSignIn;
    private EditText mTextPassword;
    private EditText mTextID;
    private static final int BLANK_STATE = 1, TRUE_STATE = 2, MINIMUM_LENGTH_STATE = 3;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static String sId, sPassword;

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
        mButtonSignUp.setOnClickListener(this);
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), Constants.FONT);
        mTextView.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_button:
                String id = mTextID.getText().toString();
                String password = mTextPassword.getText().toString();
                signUp(id, password);
                break;
        }
    }

    private void signUp(final String id, final String password) {
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseAuth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(SignUpActivity.this, "success ", Toast.LENGTH_SHORT).show();
//                    signIn(id, password);
//                } else {
//                   // Toast.makeText(SignUpActivity.this, "" + task.getResult(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private int checkData(String id, String password) {
        if (id.equals("") || password.equals("")) {
            return BLANK_STATE;
        } else if (id.length() < MINIMUM_LENGTH || password.length() < MINIMUM_LENGTH) {
            return MINIMUM_LENGTH_STATE;
        } else {
            return BLANK_STATE;
        }
    }

    private void signIn(final String id, final String password) {
//        mFirebaseAuth.signInWithEmailAndPassword(id, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(SignUpActivity.this, "" + task.getResult(), Toast.LENGTH_SHORT).show();
//                } else {
//                    sId = id;
//                    sPassword = password;
//                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
//
    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
    startActivity(intent);
    }
}
