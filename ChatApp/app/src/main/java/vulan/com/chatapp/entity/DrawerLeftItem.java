package vulan.com.chatapp.entity;

/**
 * Created by VULAN on 10/21/2016.
 */

public class DrawerLeftItem {
    private String mTitle;
    private int mImageDrawble;

    public DrawerLeftItem(String mTitle, int mImageDrawble) {
        this.mTitle = mTitle;
        this.mImageDrawble = mImageDrawble;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getImageDrawble() {
        return mImageDrawble;
    }

    public void setImageDrawble(int mImageDrawble) {
        this.mImageDrawble = mImageDrawble;
    }
}
