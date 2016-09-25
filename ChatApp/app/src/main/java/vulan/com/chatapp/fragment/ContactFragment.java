package vulan.com.chatapp.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vulan.com.chatapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment implements View.OnClickListener {

    private Button mButtonPlus;
    private Button mButtonSubtract;
    private EditText mFirstText;
    private EditText mSecondText;
    private TextView mTextResult;
    private int first;
    private int second;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void onCreateContentView(View rootView) {
        findView(rootView);
    }

    private void findView(View rootView) {
        mButtonPlus = (Button) rootView.findViewById(R.id.button_plus);
        mButtonSubtract = (Button) rootView.findViewById(R.id.button_subtract);
        mFirstText = (EditText) rootView.findViewById(R.id.edt_1);
        mSecondText = (EditText) rootView.findViewById(R.id.edt_2);
        mTextResult = (TextView) rootView.findViewById(R.id.text_kq);
        mButtonPlus.setOnClickListener(this);
        mButtonSubtract.setOnClickListener(this);
       // first = Integer.parseInt(mFirstText.getText().toString());
       // second = Integer.parseInt(mSecondText.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_plus:

//                if (mFirstText.getText()>='0') {
//
//                }
                mTextResult.setText(add(first, second));
                ;
            case R.id.button_subtract:
                mTextResult.setText(subtract(first, second));
                ;
        }
    }

    private int add(int a, int b) {
        return a + b;
    }

    private int subtract(int a, int b) {
        return a - b;
    }
}
