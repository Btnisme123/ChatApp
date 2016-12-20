package vulan.com.chatapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

import vulan.com.chatapp.R;
import vulan.com.chatapp.util.Constants;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View view;
    private CardView mCardViewGallery, mCardViewCamera;
    public static int value;
    private Activity mActivity;
    public CustomDialog(Context context) {
        super(context);
        this.context = context;
        mActivity= (Activity) context;
        view = View.inflate(this.context, R.layout.custom_dialog, null);
        findViews(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        setContentView(view);
    }

    private void findViews(View view) {
        mCardViewGallery = (CardView) view.findViewById(R.id.button_choose_gallery);
        mCardViewCamera = (CardView) view.findViewById(R.id.button_choose_camera);
        mCardViewGallery.setOnClickListener(this);
        mCardViewCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_choose_gallery) {
            Intent galleryIntent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mActivity.startActivityForResult(galleryIntent,Constants.GALLERY_CODE);
        } else {
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mActivity.startActivityForResult(intent,Constants.CAMERA_CODE);
        }
        cancel();
    }
}
