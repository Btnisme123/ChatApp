package vulan.com.chatapp.util;

import java.util.ArrayList;
import java.util.List;

import vulan.com.chatapp.R;
import vulan.com.chatapp.entity.ChatRoom;
import vulan.com.chatapp.entity.Contact;
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

    public static List<ChatRoom> getDataRoom() {
        List<ChatRoom> list = new ArrayList<>();
        list.add(new ChatRoom("thanh:Hôm nay họp nhé", "MTA", ""));
        list.add(new ChatRoom("Đông:Đi chơi không ", "CLG", ""));
        list.add(new ChatRoom("Cường:cần một người gánh", "HUST", ""));
        list.add(new ChatRoom("Việt: Mid or feed", "PTIT", ""));
        list.add(new ChatRoom("Vinh:Gánh nặng của team", "FT", ""));
        return list;
    }

    public static List<Contact> getDataContact() {
        List<Contact> list = new ArrayList<>();
        list.add(new Contact(R.drawable.avatar, "Nguyễn Vũ Lân"));
        list.add(new Contact(R.drawable.thanh, "Nguyễn Hữu Thanh"));
        list.add(new Contact(R.drawable.cuong, "Dương Vũ Thái Cường"));
        list.add(new Contact(R.drawable.dangtung, "Nguyễn Đăng Tùng"));
        list.add(new Contact(R.drawable.son, "Nguyễn Ngọc Quyết Sơn"));
        return list;
    }
}
