package vulan.com.chatapp.util;


import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import vulan.com.chatapp.entity.MessageUser;

/**
 * Created by VULAN on 9/19/2016.
 */
public class MessageDataSource {
    private static final Firebase sRef = new Firebase("https://chatapp-a87a2.firebaseio.com");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMmmss");
    public static String COLUMN_TEXT = "text";
    public static String COLUMN_SENDER = "sender";

    public static void saveMessage(MessageUser message, String Id) {
        Date date = message.getDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getText());
        msg.put(COLUMN_SENDER, "Ehai");
        //sRef.child(Id).child(key).setValue(msg);
        sRef.child("mot").setValue("hai");
    }

    public static MessageListener addMessageListener(String id, MessageCallback messageCallback) {
        MessageListener messageListener = new MessageListener(messageCallback);
        sRef.child(id).addChildEventListener(messageListener);
        return messageListener;
    }

    public static void stopListener(MessageListener mListener) {
        sRef.removeEventListener(mListener);
    }

    public static class MessageListener implements ChildEventListener {
        private MessageCallback callback;

        public MessageListener(MessageCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String, String> msg = (HashMap<String, String>) dataSnapshot.getValue();
            MessageUser messageUser = new MessageUser();
            messageUser.setSender(msg.get(COLUMN_SENDER));
            messageUser.setText(msg.get(COLUMN_TEXT));
            try {
                messageUser.setDate(sDateFormat.parse(dataSnapshot.getKey()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (callback != null) {
                callback.onMessageAdded(messageUser);
            }
            Log.e("child added ", "");
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.e("child cancel ", "");
        }
    }
    
    public interface MessageCallback {
        void onMessageAdded(MessageUser messageUser);
    }
}
