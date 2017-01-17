package vulan.com.chatapp.newtype.model;

/**
 * Created by VULAN on 10/18/2016.
 */

public class ChatRoom {

    private String mTitle;
    private String mContent;
    private String mImageUrl;

    public ChatRoom(String mContent, String mTitle, String mImageUrl) {
        this.mContent = mContent;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
