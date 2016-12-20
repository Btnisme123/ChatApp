package vulan.com.chatapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import vulan.com.chatapp.R;
import vulan.com.chatapp.dialog.CustomDialog;
import vulan.com.chatapp.util.Constants;
import vulan.com.chatapp.util.ProfileUtil;

public class ChangingPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTextUsername, mTextPassword;
    private Button mButtonSave, mButtonBack;
    private CircleImageView mImageAvatar;
    private StorageReference mStorageReference;
    private Uri mImageFilePathCamera;
    private String mImagePathGallery;
    private int mCode;
    private String mId;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing_password);
        mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://chatapp-a87a2.appspot.com");
        findView();
    }

    private void findView() {
        mTextPassword = (EditText) findViewById(R.id.edit_password);
        mTextUsername = (EditText) findViewById(R.id.edit_user_name);
        mButtonSave = (Button) findViewById(R.id.button_change);
        mImageAvatar = (CircleImageView) findViewById(R.id.img_avatar);
        mButtonBack = (Button) findViewById(R.id.btn_back);
        mImageAvatar.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mId = mTextUsername.getText().toString().trim();
        mPassword = mTextPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.button_change:
                switch (checkData(mId, mPassword)) {
                    case Constants.TRUE_STATE:
                        if (mCode == Constants.CAMERA_CODE) {
                            final StorageReference filePath = mStorageReference.child("Photos").child(mImageFilePathCamera.getLastPathSegment());
                            filePath.putFile(mImageFilePathCamera).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangingPasswordActivity.this, "Failure", Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Toast.makeText(ChangingPasswordActivity.this, "Successfull", Toast.LENGTH_LONG).show();
                                            ProfileUtil.getInstance(ChangingPasswordActivity.this).updateCurrentUserProfile(mId, mPassword, uri);
                                        }
                                    });

                                }
                            });
                        } else {
                            final StorageReference filePath = mStorageReference.child("Photos").child(mImagePathGallery);
                            filePath.putFile(mImageFilePathCamera).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangingPasswordActivity.this, "Failure", Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            ProfileUtil.getInstance(ChangingPasswordActivity.this).updateCurrentUserProfile(mId, mPassword, uri);
                                        }
                                    });

                                }
                            });
                        }
                        break;
                    case Constants.MINIMUM_LENGTH_STATE:
                        if (mTextUsername.getText().toString().length() < 6) {
                            mTextUsername.setError(Constants.ERROR_MIN_CHARACTER);
                        }
                        if (mTextPassword.getText().toString().length() < 6) {
                            mTextPassword.setError(Constants.ERROR_MIN_CHARACTER);
                        }
                }
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.img_avatar:
                CustomDialog customDialog = new CustomDialog(this);
                customDialog.show();
                break;
        }
    }

    private int checkData(String id, String password) {
        if (id.length() < Constants.MINIMUM_LENGTH || password.length() < Constants.MINIMUM_LENGTH) {
            return Constants.MINIMUM_LENGTH_STATE;
        }
        return Constants.TRUE_STATE;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CAMERA_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImageAvatar.setImageBitmap(photo);
            mImageFilePathCamera = data.getData();
        }
        if (requestCode == Constants.GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            mImageFilePathCamera = data.getData();
            String[] filePathColum = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(mImageFilePathCamera, filePathColum, null, null, null);
            if (cursor != null) {
                mImagePathGallery = "";
                if (cursor.moveToFirst()) {
                    int columIndex = cursor.getColumnIndexOrThrow(filePathColum[0]);
                    String path = cursor.getString(columIndex);
                    int startPosition = path.lastIndexOf('/');
                    int length = path.length();
                    for (int i = startPosition + 1; i < length; i++) {
                        mImagePathGallery += path.charAt(i);
                    }
                }
                mImageAvatar.setImageURI(mImageFilePathCamera);
                cursor.close();
            }
        }
        mCode = requestCode;
    }
}
