package vulan.com.chatapp.entity;

import java.util.Date;

/**
 * Created by VULAN on 9/19/2016.
 */
public class MessageUser {
    private String mText;
    private String mSender;
    private Date mDate;

    public MessageUser() {

    }

    public MessageUser(String mText, String mSender) {
        this.mText = mText;
        this.mSender = mSender;
    }
    public MessageUser(String mText, String mSender, Date mDate) {
        this.mText = mText;
        this.mSender = mSender;
        this.mDate = mDate;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
}
