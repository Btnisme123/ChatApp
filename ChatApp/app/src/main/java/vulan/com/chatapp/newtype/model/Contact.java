package vulan.com.chatapp.newtype.model;

/**
 * Created by VULAN on 10/18/2016.
 */

public class Contact {
    private String mImageUrl;
    private String mName;

    public int getmImageFake() {
        return mImageFake;
    }

    public void setmImageFake(int mImageFake) {
        this.mImageFake = mImageFake;
    }

    private int mImageFake;

    public Contact(String mImageUrl, String mName) {
        this.mImageUrl = mImageUrl;
        this.mName = mName;
    }

    public Contact(int imageFake, String mName) {
        this.mImageFake = imageFake;
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
