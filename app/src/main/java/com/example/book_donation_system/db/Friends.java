package com.example.book_donation_system.db;

/**
 * Created by 何书杰 on 2017/10/17.
 */

public class Friends {
    private String friends_item_userName;
    private int friends_item_Circleimageid;
    private String friends_item_qianMing;
    public Friends(String friends_item_userName,String friends_item_qianMing,int friends_item_Circleimageid){
        this.friends_item_userName = friends_item_userName;
        this.friends_item_qianMing = friends_item_qianMing;
        this.friends_item_Circleimageid = friends_item_Circleimageid;
    }
    public void setFriends_item_Circleimageid(int friends_item_Circleimageid) {
        this.friends_item_Circleimageid = friends_item_Circleimageid;
    }

    public void setFriends_item_qianMing(String friends_item_qianMing) {
        this.friends_item_qianMing = friends_item_qianMing;
    }

    public void setFriends_item_userName(String friends_item_userName) {
        this.friends_item_userName = friends_item_userName;
    }

    public int getFriends_item_Circleimageid() {
        return friends_item_Circleimageid;
    }

    public String getFriends_item_qianMing() {
        return friends_item_qianMing;
    }

    public String getFriends_item_userName() {
        return friends_item_userName;
    }
}
