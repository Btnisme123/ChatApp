package vulan.com.chatapp.util;

import java.util.ArrayList;
import java.util.List;

import vulan.com.chatapp.entity.MessageUser;

/**
 * Created by VULAN on 9/20/2016.
 */
public class FakeContainer {

    public static List<MessageUser> getData() {
        List<MessageUser> list = new ArrayList<>();
        // list.add(new MessageUser("111111111111111111111111111111111111111", "23"));
        //list.add(new MessageUser("111111111111111111111113111111111111111", "23"));
        //list.add(new MessageUser("111111111111111111111111411111111111111", "23"));
        return list;
    }
}
