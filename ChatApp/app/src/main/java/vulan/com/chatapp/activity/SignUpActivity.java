package vulan.com.chatapp.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import vulan.com.chatapp.R;
import vulan.com.chatapp.util.Constants;

public class SignUpActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findView();
        init();
    }

    private void findView() {
        mTextView = (TextView) findViewById(R.id.text_logo);
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), Constants.FONT);
        mTextView.setTypeface(typeface);
    }
}
