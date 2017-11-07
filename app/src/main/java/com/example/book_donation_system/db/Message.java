package com.example.book_donation_system.db;

/**
 * Created by 何书杰 on 2017/10/13.
 */

public class Message {
    private String MessageContent;
    private int Message_Id_1;
    private int Message_Id_2;
    public Message(String MessageContent,int Message_Id_1,int Message_Id_2){
        this.MessageContent = MessageContent;
        this.Message_Id_1 = Message_Id_1;
        this.Message_Id_2 = Message_Id_2;
    }
    public int getMessage_Id_1() {
        return Message_Id_1;
    }

    public int getMessage_Id_2() {
        return Message_Id_2;
    }

    public String getMessageContent() {
        return MessageContent;
    }
}
