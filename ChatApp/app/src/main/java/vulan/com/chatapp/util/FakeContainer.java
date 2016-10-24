package vulan.com.chatapp.util;

import java.util.ArrayList;
import java.util.List;

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
        list.add(new ChatRoom("Thanh:Hom nay hop nhe", "MTA", ""));
        list.add(new ChatRoom("Thanh:Hom nay hop nhe", "CLG", ""));
        list.add(new ChatRoom("Thanh:Hom nay hop nhe", "HUST", ""));
        list.add(new ChatRoom("Thanh:Hom nay hop nhe", "PTIT", ""));
        list.add(new ChatRoom("Thanh:Hom nay hop nhe", "FT", ""));
        return list;
    }

    public static List<Contact> getDataContact() {
        List<Contact> list = new ArrayList<>();
        list.add(new Contact("Thanh", "MTA"));
        list.add(new Contact("Hoa", "MTA"));
        list.add(new Contact("Dong", "MTA"));
        list.add(new Contact("Cuong", "MTA"));
        list.add(new Contact("Hung", "MTA"));
        return list;
    }
}
